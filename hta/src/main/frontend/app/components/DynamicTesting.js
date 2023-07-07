import CalorieIntakeService from '@/app/service/CalorieIntakeService';
import React, { useState, useEffect } from 'react';

const Testfile = () => {
  const [dynamicData, setDynamicData] = useState({
    dynamicInt: '',
    dynamicResult: ''
  });

  useEffect(() => {
    const sendInterval = setInterval(() => {
      if (dynamicData.dynamicInt !== '') {
        CalorieIntakeService.sendDynamicData(dynamicData)
          .then((response) => {
            console.log(response.data);
          })
          .catch(error => {
            console.log("Cannot be added");
            console.log(error);
          });
      }
    }, 1000);
  
    const fetchInterval = setInterval(() => {
      CalorieIntakeService.getDynamicData()
        .then((response) => {
          const dynamicResult = response.data;
          setDynamicData(prevData => ({ ...prevData, dynamicResult }));
          console.log(dynamicResult);
        })
        .catch(error => {
          console.log("Cannot retrieve data");
          console.log(error);
        });
    }, 1000);
  
    return () => {
      clearInterval(sendInterval); 
      clearInterval(fetchInterval); 
    };
  }, [dynamicData.dynamicInt]);

  const handleChange = (e) => {
    const value = parseInt(e.target.value);
    setDynamicData((prevData) => ({ ...prevData, dynamicInt: value }));
  };

  return (
    <div>
      <input
        type="number"
        value={dynamicData.dynamicInt}
        onChange={(e) => handleChange(e)}
      />
      {dynamicData.dynamicResult !== null && (
        <div>Dynamic Result: {dynamicData.dynamicResult}</div>
      )}
    </div>
  );
};

export default Testfile;