export const apiRoot = "http://localhost/api";
export const srcRoot = "http://localhost:8080/";

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