'use client'
import { useState, useEffect, useRef } from 'react';
import PropTypes from 'prop-types';
import {alertService, AlertType} from "@/app/service/AlertService";
import Cookies from "js-cookie";

export { Alert };

Alert.propTypes = {
    id: PropTypes.string,
    fade: PropTypes.bool,
    filterType: PropTypes.string
};

Alert.defaultProps = {
    id: 'default-alert',
    fade: true,
    filterType: null
};

function Alert({ id, fade, filterType }) {
    const mounted = useRef(false);
    const [alerts, setAlerts] = useState([]);

    useEffect(() => {
        mounted.current = true;
        alertService.onAlert(id)
            .subscribe(alert => {

                if (!alert.message) {
                    setAlerts(alerts => {
                        const filteredAlerts = alerts.filter(x => x.keepAfterRouteChange);
                        return omit(filteredAlerts, 'keepAfterRouteChange');
                    });
                } else {
                    alert.itemId = Math.random();
                    setAlerts(alerts => ([...alerts, alert]));
                    if (alert.autoClose) {
                        if (alert.type === AlertType.Info) {
                            setTimeout(() => removeAlert(alert), 15000);
                        } else {
                            setTimeout(() => removeAlert(alert), 8000);
                        }
                    }
                }
            });
    }, []);

    function omit(arr, key) {
        return arr.map(obj => {
            const { [key]: omitted, ...rest } = obj;
            return rest;
        });
    }

    function notificationCookieSetting(alert) {
        if (alert.type === AlertType.Info) {
            let confirmationKey = alert.message;

            const confirmed = Cookies.get(confirmationKey);
            const currentDate = new Date();

            if (!confirmed || currentDate > new Date(confirmed)) {
                const expirationDate = new Date();
                expirationDate.setDate(expirationDate.getDate() + 1);
                expirationDate.setUTCHours(0, 0, 0, 0)

                Cookies.set(confirmationKey, expirationDate.toJSON(), {
                    expires: expirationDate,
                });
            }
        }
    }

    function removeAlert(alert) {
        if (!mounted.current) return;
        if (fade) {
            setAlerts(alerts => alerts.map(x => x.itemId === alert.itemId ? { ...x, fade: true } : x));

            setTimeout(() => {
                setAlerts(alerts => alerts.filter(x => x.itemId !== alert.itemId));
            }, 250);
        } else {
            setAlerts(alerts => alerts.filter(x => x.itemId !== alert.itemId));
        }
    }


    function cssClasses(alert) {
        if (!alert) return;

        const classes = ['alert', 'alert-dismissible'];

        const alertTypeClass = {
            [AlertType.Success]: 'flex justify-between rounded w-2/3 mx-auto my-8 text-center bg-green-500 text-white py-2 px-6 font-bold',
            [AlertType.Error]: 'flex justify-between rounded w-2/3 mx-auto my-8 text-center bg-red-500 text-white py-2 px-6 font-bold',
            [AlertType.Info]: 'flex justify-between rounded w-2/3 mx-auto my-8 text-center bg-sky-300 text-white py-2 px-6 font-bold',
            [AlertType.Warning]: 'flex justify-between rounded w-1/2 my-8 text-center bg-yellow-800 text-white py-2 px-6 font-bold'
        }

        classes.push(alertTypeClass[alert.type]);

        if (alert.fade) {
            classes.push('fade');
        }
        return classes.join(' ');
    }

    if (!alerts.length) return null;

    return (
        <div>
            {alerts
                .filter(alert => !filterType || alert.type === filterType)
                .map((alert, index) =>
                <div  key={index} className={cssClasses(alert)}>
                    <span dangerouslySetInnerHTML={{ __html: alert.message }} className='pr-6'></span>
                    <a className="close cursor-pointer" onClick={() => {removeAlert(alert); notificationCookieSetting(alert)}}>&times;</a>
                </div>
            )}
        </div>
    );
}