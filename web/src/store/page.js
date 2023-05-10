import {defineStore} from 'pinia';

export const pageStore = defineStore('page', {
    state: () => {
        return {
            current: 1,
            total: 0,
            size: 30,
            first: true,
        };
    },
    getters: {},
    actions: {
        setPage(current, total, size) {
            this.current = current;
            this.total = total;
            this.size = size;
            this.first = false;
        }
    }
});
