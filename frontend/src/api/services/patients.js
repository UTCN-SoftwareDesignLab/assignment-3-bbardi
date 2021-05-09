import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    fetchAll() {
        return HTTP.get(BASE_URL + "/clinic/patient", { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    fetchNames() {
        return HTTP.get(BASE_URL + "/clinic/patient", { headers: authHeader() }).then(
            (response) => {
                return response.data.map(patient => patient.name);
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    deletePatient(patient) {
        return HTTP.delete(BASE_URL + `/clinic/patient/${patient.id}`, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    createPatient(patient) {
        return HTTP.post(BASE_URL + "/clinic/patient", patient, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    editPatient(patient) {
        return HTTP.patch(BASE_URL + `/clinic/patient/${patient.id}`, patient, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
};