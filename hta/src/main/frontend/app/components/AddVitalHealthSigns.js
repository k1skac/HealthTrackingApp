import VitalHealthSignService from '@/app/service/VitalHealthSignService';
import { React, useState, useRef } from 'react'

const AddVitalHealthSigns = () => {
    const [saveHeartRateDTO, setSaveHeartRateDTO] = useState({
        heartRate: '',
        heartRateMeasuredAt: '',
        heartRateFile: ''
    });

    const heartRateFileValue = useRef(null);

    const [saveWeightDTO, setSaveWeightDTO] = useState({
        weight: '',
        weightMeasuredAt: '',
        weightFile: ''
    });

    const weightFileValue = useRef(null);

    const [saveBloodPressureDTO, setSaveBloodPressureDTO] = useState({
        systolic: '',
        diastolic: '',
        bloodPressureMeasuredAt: '',
        bloodPressureFile: ''
    });

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
        if (saveWeightDTO.weight !== '' & saveWeightDTO.weightMeasuredAt !== '') {
            e.preventDefault();

            const formData = new FormData();
            formData.append('weight', saveWeightDTO.weight)
            formData.append('weightMeasuredAt', saveWeightDTO.weightMeasuredAt);
            if (saveWeightDTO.weightFile !== '') {
                formData.append('weightFile', saveWeightDTO.weightFile);
            }

            VitalHealthSignService.saveWeight(formData)
            VitalHealthSignService.saveWeight(saveWeightDTO)
            .then((response) => {
                console.log(response.data)
            }).catch((error) => {
                console.log('Weight cannot be added!')
                console.log(error);
            });
        } 
        if (saveHeartRateDTO.heartRate !== '' && saveHeartRateDTO.heartRateMeasuredAt !== '') {
            e.preventDefault();

            const formData = new FormData();
            formData.append('heartRate', saveHeartRateDTO.heartRate)
            formData.append('heartRateMeasuredAt', saveHeartRateDTO.heartRateMeasuredAt);
            if (saveHeartRateDTO.heartRateFile !== '') {
                formData.append('heartRateFile', saveHeartRateDTO.heartRateFile);
            }

            VitalHealthSignService.saveHeartRate(formData)
            .then((response) => {
                console.log(response.data);
            }).catch((error) => {
                console.log('Heart rate cannot be added!')
                console.log(error.response);
            });
        }
        if (saveBloodPressureDTO.systolic !== '' && saveBloodPressureDTO.bloodPressureMeasuredAt !== '' && saveBloodPressureDTO.diastolic !== '') {
            e.preventDefault();

            const formData = new FormData();
            formData.append('systolic', saveBloodPressureDTO.systolic)
            formData.append('diastolic', saveBloodPressureDTO.diastolic)
            formData.append('bloodPressureMeasuredAt', saveBloodPressureDTO.bloodPressureMeasuredAt);
            if (saveBloodPressureDTO.bloodPressureFile !== '') {
                formData.append('bloodPressureFile', saveBloodPressureDTO.bloodPressureFile);
            }


            VitalHealthSignService.saveBloodPressure(formData)
                .then((response) => {
                    console.log(response.data)
                }).catch((error) => {
                console.log('Blood pressure cannot be added!')
                console.log(error);
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
        <div className='m-auto w-full max-w-screen-md p-6 my-12 overflow-hidden text-left align-middle transition-all transform bg-white shadow-xl rounded-md'>
            <div>
                <a href="http://localhost:3000/login">Login</a>
            </div>
            <div>
                <h1 className='rounded w-full my-8 text-center bg-sky-800 text-white py-2 px-6 font-bold capitalize'>Add your vital health signs</h1>
            </div>
            <div className='flex h-80'>
                <div className='py-2 h-14 my-4 mx-3'>
                    <label className=' text-gray-800 text-sm font-normal'>Heart Rate:
                        <input 
                            type="number" 
                            step="0.01"
                            name="heartRate" 
                            placeholder="...bpm"
                            value={saveHeartRateDTO.heartRate}
                            onChange={(e) => handleChange(e)}
                            className='h-10 w-56 border mt-2 px-2 py2'
                        />
                    </label>
                    <label className='text-gray-800 text-sm font-normal'>Date of Measurement:
                        <input 
                            type="datetime-local"
                            name="heartRateMeasuredAt" 
                            value={saveHeartRateDTO.heartRateMeasuredAt}
                            onChange={(e) => handleChange(e)}
                            className='h-10 w-56 border mt-2 px-2 py2'
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
                <div className='py-2 h-14 my-4'>
                    <label className='text-gray-800 text-sm font-normal'>Weight:
                        <input 
                            type="number" 
                            step="0.01"
                            name="weight" 
                            placeholder="...kg"
                            value={saveWeightDTO.weight}
                            onChange={(e) => handleChange(e)}
                            className='h-10 w-56 border mt-2 px-2 py2'
                        />
                    </label>
                    <label className='text-gray-800 text-sm font-normal'>Date of Measurement:
                        <input 
                            type="datetime-local" 
                            name="weightMeasuredAt" 
                            value={saveWeightDTO.weightMeasuredAt}
                            onChange={(e) => handleChange(e)}
                            className='h-10 w-56 border mt-2 px-2 py2'
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
                <div className='py-2 h-14 my-4 mx-3'>
                    <label className=' text-gray-800 text-sm font-normal'>Systolic Blood Pressure:
                        <input
                            type="number"
                            step="0.01"
                            name="systolic"
                            placeholder="...mmHg"
                            value={saveBloodPressureDTO.systolic}
                            onChange={(e) => handleChange(e)}
                            className='h-10 w-56 border mt-2 px-2 py2'
                        />
                    </label>
                    <label className=' text-gray-800 text-sm font-normal'>Diastolic Blood Pressure:
                        <input
                            type="number"
                            step="0.01"
                            name="diastolic"
                            placeholder="...mmHg"
                            value={saveBloodPressureDTO.diastolic}
                            onChange={(e) => handleChange(e)}
                            className='h-10 w-56 border mt-2 px-2 py2'
                        />
                    </label>
                    <label className='text-gray-800 text-sm font-normal'>Date of Measurement:
                        <input
                            type="datetime-local"
                            name="bloodPressureMeasuredAt"
                            value={saveBloodPressureDTO.bloodPressureMeasuredAt}
                            onChange={(e) => handleChange(e)}
                            className='h-10 w-56 border mt-2 px-2 py2'
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
            <div className=' inline-flex'>
                <button 
                    type="submit" 
                    onClick={saveVitalHealthSign}
                    className='rounded text-white font-semibold bg-green-500 hover:bg-green-700 py-2 px-6'>
                    Save
                </button>
                <button
                    type="reset"
                    onClick={reset}
                    className='rounded text-white font-semibold bg-orange-500 hover:bg-orange-700 py-2 px-6 ml-6'>
                    Clear
                </button>
            </div>
        </div>
    )
}

export default AddVitalHealthSigns