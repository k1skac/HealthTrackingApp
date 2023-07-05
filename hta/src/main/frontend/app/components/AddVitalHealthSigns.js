import VitalHealthSignService from '@/app/service/VitalHealthSignService';
import { React, useState, useEffect, useRef } from 'react'
import {alertService, AlertType} from "@/app/service/AlertService";
import { Alert } from "@/app/components/Alert";
import {Fragment} from "react";
import {Dialog, Transition} from "@headlessui/react";

const AddVitalHealthSigns = () => {
    const  [isOpen, setIsOpen] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(false);
    }, []);

    const [saveHeartRateDTO, setSaveHeartRateDTO] = useState({
        heartRate: '',
        heartRateMeasuredAt: '',
        heartRateFile: null
    });

    const heartRateFileValue = useRef(null);

    const [saveWeightDTO, setSaveWeightDTO] = useState({
        weight: '',
        weightMeasuredAt: '',
        weightFile: null
    });

    const weightFileValue = useRef(null);

    const [saveBloodPressureDTO, setSaveBloodPressureDTO] = useState({
        systolic: '',
        diastolic: '',
        bloodPressureMeasuredAt: '',
        bloodPressureFile: null
    });

    function closeModal() {
        setIsOpen(false);
    }
    function openModal() {
        setIsOpen(true);
    }

    const bloodPressureFileValue = useRef(null);

    const handleChange = (e) => {
        const { name, value, type } = e.target;

        if (type === 'file') {
            const file = e.target.files[0];
            setSaveWeightDTO({...saveWeightDTO, weightFile: file});
            setSaveHeartRateDTO({...saveHeartRateDTO, heartRateFile: file});
            setSaveBloodPressureDTO({...saveBloodPressureDTO, bloodPressureFile: file});
        } else {
            setSaveWeightDTO({...saveWeightDTO, [name]: value});
            setSaveHeartRateDTO({...saveHeartRateDTO, [e.target.name]: value});
            setSaveBloodPressureDTO({...saveBloodPressureDTO, [e.target.name]: value});
        }
    }

    const saveVitalHealthSign = (e) => {

        if (saveWeightDTO.weight !== '' && saveWeightDTO.weightMeasuredAt !== '') {
            e.preventDefault();
            const formData = new FormData();
            formData.append('weight', saveWeightDTO.weight)
            formData.append('weightMeasuredAt', saveWeightDTO.weightMeasuredAt);
            if (saveWeightDTO.weightFile !== null && saveWeightDTO.weightFile !== undefined) {
                formData.append('weightFile', saveWeightDTO.weightFile);
            }

            VitalHealthSignService.saveWeight(formData)
            .then((response) => {
                alertService.success(response.data)
            }).catch((error) => {
                console.log('Weight cannot be added!')
                alertService.error(error.response.data.errors[0]);
            });
        }
        if (saveHeartRateDTO.heartRate !== '' && saveHeartRateDTO.heartRateMeasuredAt !== '') {
            e.preventDefault();

            const formData = new FormData();
            formData.append('heartRate', saveHeartRateDTO.heartRate)
            formData.append('heartRateMeasuredAt', saveHeartRateDTO.heartRateMeasuredAt);
            if (saveHeartRateDTO.heartRateFile !== null && saveHeartRateDTO.heartRateFile !== undefined) {
                formData.append('heartRateFile', saveHeartRateDTO.heartRateFile);
            }

            VitalHealthSignService.saveHeartRate(formData)
            .then((response) => {
                alertService.success(response.data)
            }).catch((error) => {
                console.log('Heart rate cannot be added!')
                alertService.error(error.response.data.errors[0]);
            });
        }
        if (saveBloodPressureDTO.systolic !== '' && saveBloodPressureDTO.bloodPressureMeasuredAt !== '' && saveBloodPressureDTO.diastolic !== '') {
            e.preventDefault();

            const formData = new FormData();
            formData.append('systolic', saveBloodPressureDTO.systolic)
            formData.append('diastolic', saveBloodPressureDTO.diastolic)
            formData.append('bloodPressureMeasuredAt', saveBloodPressureDTO.bloodPressureMeasuredAt);
            if (saveBloodPressureDTO.bloodPressureFile !== null && saveBloodPressureDTO.bloodPressureFile !== undefined) {
                formData.append('bloodPressureFile', saveBloodPressureDTO.bloodPressureFile);
            }

            VitalHealthSignService.saveBloodPressure(formData)
                .then((response) => {
                    alertService.success(response.data);
                }).catch((error) => {
                console.log('Blood pressure cannot be added!')
                for (const element of error.response.data.errors) {
                    alertService.error(element);
                }
                });
        }
    }

    const reset = (e) => {
        e.preventDefault();
        setSaveHeartRateDTO({
            heartRate: '',
            heartRateMeasuredAt: ''
        });

        setSaveWeightDTO({
            weight: '',
            weightMeasuredAt: ''
        });

        setSaveBloodPressureDTO({
            systolic: '',
            diastolic: '',
            bloodPressureMeasuredAt: ''
        });

        heartRateFileValue.current.value = null;
        weightFileValue.current.value = null;
        bloodPressureFileValue.current.value = null;
    }

    return (
        <div>
            <button
                onClick={openModal}
                className='rounded bg-htadarkteal hover:bg-htadarktealhover text-white w-48 py-3 shadow-cyan-950 shadow-md font-semi'>
                Add Vital Health Signs
            </button>
            <Transition appear show={isOpen} as={Fragment}>
                <div className='m-auto w-full max-w-screen-md overflow-hidden text-left align-middle transition-all transform shadow-xl rounded-md'>
                    <Dialog
                        as="div"
                        className='fixed inset-24 max-w-screen-lg mx-auto'
                        onClose={closeModal}>
                        <div className='text-center'>
                            <Transition.Child
                                as={Fragment}
                                enter='ease-out duration-300'
                                enterFrom='opacity-0 scale-95'
                                enterTo='opacity-100 scale-100'
                                leave='ease-in duration-200'
                                leaveFrom='opacity-100 scale-100'
                                leaveTo='opacity-0 scale-95'>
                                <div>
                                    <Dialog.Title
                                        as='div'
                                        className='mb-1 bg-htadarkteal rounded-md py-5 shadow-slate-900 shadow-md'>
                                        <h3
                                            className='text-lg font-bold text-white m-auto'>
                                            Add your vital health signs
                                        </h3>
                                    </Dialog.Title>
                                    <div className='bg-htamediumteal rounded-md shadow-slate-900 shadow-md'>
                                        <div className='pt-6 '>
                                             <Alert filterType={!AlertType.Info}/>
                                        </div>
                                        <div className='m-auto max-w-md text-white inline-flex justify-center'>
                                            <div className='pt-28'>
                                                <label
                                                    className='block py-2 px-4'>
                                                    Heart Rate:
                                                    <input
                                                        type="number"
                                                        step="0.01"
                                                        name="heartRate"
                                                        placeholder="...bpm"
                                                        value={saveHeartRateDTO.heartRate}
                                                        onChange={(e) => handleChange(e)}
                                                        className='rounded-sm h-8 w-60 text-black shadow-slate-900 shadow-sm'
                                                    />
                                                </label>
                                                <label
                                                    className='block pt-2 py-2'>
                                                    Date of Measurement:
                                                    <input
                                                        type="datetime-local"
                                                        name="heartRateMeasuredAt"
                                                        value={saveHeartRateDTO.heartRateMeasuredAt}
                                                        onChange={(e) => handleChange(e)}
                                                        className='rounded-sm h-8 w-60 text-black shadow-slate-900 shadow-sm'
                                                    />
                                                </label>
                                                <label className='text-gray-800 text-sm font-normal'>Upload File:
                                                    <input
                                                        id = "heartRateFile"
                                                        type="file"
                                                        name="heartRateFile"
                                                        ref={heartRateFileValue}
                                                        onChange={(e) => handleChange(e)}
                                                        className='w-56 mt-2 px-2 py2'
                                                    />
                                                </label>
                                            </div>
                                            <div className='pt-28'>
                                                <label
                                                    className='block py-2 px-4'>
                                                    Weight:
                                                    <input
                                                        type="number"
                                                        step="0.01"
                                                        name="weight"
                                                        placeholder="...kg"
                                                        value={saveWeightDTO.weight}
                                                        onChange={(e) => handleChange(e)}
                                                        className='h-8 w-60 border px-2 py2 text-black shadow-slate-900 shadow-sm'
                                                    />
                                                </label>
                                                <label
                                                    className='block pt-2 py-2'>
                                                    Date of Measurement:
                                                    <input
                                                        type="datetime-local"
                                                        name="weightMeasuredAt"
                                                        value={saveWeightDTO.weightMeasuredAt}
                                                        onChange={(e) => handleChange(e)}
                                                        className='rounded-sm h-8 w-60 text-black shadow-slate-900 shadow-sm'
                                                    />
                                                </label>
                                                <label className='text-gray-800 text-sm font-normal'>Upload File:
                                                    <input
                                                        id="weightFile"
                                                        type="file"
                                                        name="weightFile"
                                                        ref={weightFileValue}
                                                        onChange={(e) => handleChange(e)}
                                                        className='h-10 w-56 mt-2 px-2 py2'
                                                    />
                                                </label>
                                            </div>
                                            <div className='pt-28'>
                                                <label
                                                    className='block py-2 px-4'>
                                                    Systolic Blood Pressure:
                                                    <input
                                                        type="number"
                                                        step="0.01"
                                                        name="systolic"
                                                        placeholder="...mmHg"
                                                        value={saveBloodPressureDTO.systolic}
                                                        onChange={(e) => handleChange(e)}
                                                        className='rounded-sm h-8 w-60 text-black shadow-slate-900 shadow-sm'
                                                    />
                                                </label>
                                                <label
                                                    className='block py-2'>
                                                    Diastolic Blood Pressure:
                                                    <input
                                                        type="number"
                                                        step="0.01"
                                                        name="diastolic"
                                                        placeholder="...mmHg"
                                                        value={saveBloodPressureDTO.diastolic}
                                                        onChange={(e) => handleChange(e)}
                                                        className='rounded-sm h-8 w-60 text-black shadow-slate-900 shadow-sm'
                                                    />
                                                </label>
                                                <label
                                                    className='block pt-2 pb-11'>
                                                    Date of Measurement:
                                                    <input
                                                        type="datetime-local"
                                                        name="bloodPressureMeasuredAt"
                                                        value={saveBloodPressureDTO.bloodPressureMeasuredAt}
                                                        onChange={(e) => handleChange(e)}
                                                        className='rounded-sm h-8 w-60 text-black shadow-slate-900 shadow-sm'
                                                    />
                                                </label>
                                                <label className='text-gray-800 text-sm font-normal'>Upload File:
                                                    <input
                                                        id = "bloodPressureFile"
                                                        type="file"
                                                        name="bloodPressureFile"
                                                        ref={bloodPressureFileValue}
                                                        onChange={(e) => handleChange(e)}
                                                        className='h-10 w-56 mt-2 px-2 py2'
                                                    />
                                                </label>
                                            </div>
                                        </div>
                                        <div className='flex justify-center pt-2 pb-20'>
                                            <div className='h-14 my-4 pt-4'>
                                                <button
                                                    type="submit"
                                                    onClick={saveVitalHealthSign}
                                                    className='mx-2 rounded text-white font-semibold bg-green-500 hover:bg-green-700 py-2 px-6 shadow-slate-900 shadow-sm'>
                                                    Save
                                                </button>
                                            </div>
                                            <div className='h-14 my-4 pt-4'>
                                                <button
                                                    type="reset"
                                                    onClick={reset}
                                                    className='mx-2 rounded text-white font-semibold bg-orange-500 hover:bg-orange-700 py-2 px-6 shadow-slate-900 shadow-sm'>
                                                    Clear
                                                </button>
                                            </div>
                                            <div className='h-14 my-4 pt-4'>
                                                <button
                                                    onClick={closeModal} //Added function
                                                    className='mx-2 rounded text-white font-semibold bg-red-500 hover:bg-red-700 py-2 px-6 shadow-slate-900 shadow-sm'>
                                                    Close
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </Transition.Child>
                        </div>
                    </Dialog>
                </div>
            </Transition>
        </div>
    )
}

export default AddVitalHealthSigns;