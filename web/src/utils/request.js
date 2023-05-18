import axios from 'axios';
import router from "@/router";
import {apiRoot, token} from "@/store/meta";

const request = axios.create({
    baseURL: apiRoot,
    timeout: 15000
});

request.interceptors.request.use(config => {
        const accessToken = token();
        if (accessToken) {
            config.headers.Authorization = `Bearer ${accessToken}`;
        }
        return config;
    },
    error => {
        console.log(error);
        return Promise.reject(error);
    }
);

request.interceptors.response.use(response => {
        return response.data;
    },
    error => {
        console.log(error);
        if (error.response) {
            if (error.response.status === 401) {
                router.push('/login');
            }
        }
        return Promise.reject(error);
    }
);

export default request;
