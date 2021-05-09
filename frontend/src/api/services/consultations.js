import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    fetchAll() {
        return HTTP.get(BASE_URL + "/clinic/consult", { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    deleteConsult(consult) {
        return HTTP.delete(BASE_URL + `/clinic/consult/${consult.id}`, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    createConsult(consult) {
        return HTTP.post(BASE_URL + "/clinic/consult", consult, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    editConsult(consult) {
        return HTTP.patch(BASE_URL + `/clinic/consult/${consult.id}`, consult, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    checkin(consult){
        return HTTP.post(BASE_URL + `/clinic/consult/${consult.id}`,null, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    }
}