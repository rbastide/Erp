import { reactive, toRefs } from 'vue';
import api from "@/services/api.js";

const state = reactive({
    universityDepartments: [],
    isLoaded: false,
});

export const useDepartmentStore = () => {
    const fetchDepartments = async (force = false) => {
        if (state.isLoaded && !force) return state.universityDepartments;

        try {
            const { data } = await api.get('/universityDepartment/getUniversityDepartments');
            state.universityDepartments = data || [];
            state.isLoaded = true;
            return state.universityDepartments;
        } catch (e) {
            console.error("Erreur store départements", e);
            return [];
        }
    };

    return {
        ...toRefs(state),
        fetchDepartments
    };
};
