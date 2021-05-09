import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    fetchDoctors(){
        return HTTP.get(BASE_URL + "/clinic/planning/doctors", { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    fetchFreeTimeSlots(doctor,date){
        return HTTP.get(BASE_URL + `/clinic/planning/slots?doctorName=${doctor}&date=${date}`, { headers: authHeader() }).then(
            (response) => {
                return response.data.map(time => time.timestamp);
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
}