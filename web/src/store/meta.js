export const apiRoot = "http://10.1.19.146/api";
export const srcRoot = "http://10.1.19.146:8088/";

const userKey = "user";
const userNameKey = "username";
const tokenKey = "token";

export function login(user) {
    localStorage.setItem(user, user);
    localStorage.setItem(userNameKey, user.username);
    localStorage.setItem(tokenKey, user.accessToken);
}

export function username() {
    return localStorage.getItem(userNameKey);
}

export function token() {
    return localStorage.getItem(tokenKey);
}

export function logout(user) {
    localStorage.setItem(user, null);
    localStorage.setItem(userNameKey, "");
    localStorage.setItem(tokenKey, "");
}