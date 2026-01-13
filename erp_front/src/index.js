import  {createRouter, createWebHistory} from 'vue-router';
import LoginPage from './components/App/LoginPage.vue';
import ResourcePage from '@/components/ResourceSheet/ResourcePage.vue';
import HomePage from './components/User/HomePage.vue';
import HistoryPage from '@/components/ResourceSheet/HistoryPage.vue';
import HomeAdminPage from './components/Admin/HomeAdminPage.vue';
import CreateNewUserPage from './components/Admin/CreateNewUserPage.vue';
import HelpPage from './components/App/HelpPage.vue';
import DeconnectionPage from './components/Information/DeconnectionPage.vue';
import ErrorSave from "./components/Information/ErrorSave.vue";
import SuccessfullySaved from "./components/Information/SuccessfullySaved.vue";
import FillResourceSheet from "@/components/ResourceSheet/FillResourceSheet.vue";
import CancelPageBackToDashboard from "./components/Information/CancelPageBackToDashboard.vue";
import CancelPageMccc from "./components/Information/CancelPageMccc.vue";
import HistoryAdminPage from "./components/Admin/HistoryAdminPage.vue";
import SuccessfullyDeletedPage from "./components/Information/SuccessfullyDeleted.vue";
import McccResourcePage from "./components/Mccc/McccResourcePage.vue";
import McccFillHourPage from "./components/Mccc/McccFillHour.vue";
import ModifSaved from "./components/Information/ModifSaved.vue";
import McccMenuPage from "./components/Mccc/McccMenuPage.vue";
import McccTeachersPage from "./components/Mccc/McccTeachersPage.vue";
import McccSAE from "./components/Mccc/McccSAE.vue";
import McccSkills from "./components/Mccc/McccSkills.vue";
import McccSummaryPage from "./components/Mccc/McccSummary.vue";
import SkillsGestion from "./components/Admin/SkillsGestion.vue";
import ResourceSheetHistory from "@/components/ResourceSheet/ResourceSheetHistory.vue";
import ResourcesGestion from "./components/Admin/ResourcesGestion.vue";
import UsersGestion from "./components/Admin/UsersGestion.vue";
import SaeGestion from "./components/Admin/SaeGestion.vue";
import Settings from "./components/App/Settings.vue";


const routes = [
    {
        path: '/',
        name: 'Default',
        component: LoginPage
    },
    {
        path: '/home',
        name: 'Home',
        component: HomePage
    },
    {
        path: '/resource',
        name: 'Resource',
        component: ResourcePage
    },
    {
        path:'/history',
        name: 'History',
        component: HistoryPage
    },
    {
        path: '/login',
        name: 'Login',
        component: LoginPage
    },
    {
        path: '/home-admin',
        name: 'HomeAdmin',
        component: HomeAdminPage
    },
    {
        path: '/new-user',
        name: 'CreateNewUserPage',
        component: CreateNewUserPage
    },
    {
        path: '/help',
        name: 'HelpPage',
        component: HelpPage
    },
    {
        path: '/deconnection',
        name: 'DeconnectionPage',
        component: DeconnectionPage
    },
    {
        path: '/error-save',
        name: 'ErrorSavePage',
        component: ErrorSave
    },
    {
        path: '/successfully-saved',
        name: 'SuccessfullySavedPage',
        component: SuccessfullySaved
    },
    {
        path: '/fill-resource-sheet',
        name: 'FillResourceSheet',
        component: FillResourceSheet
    },
    {
        path: '/cancel',
        name: 'CancelPage',
        component: CancelPageBackToDashboard
    },
    {
        path: '/cancel-mccc',
        name: 'CancelPageMccc',
        component: CancelPageMccc
    },
    {
        path: '/mccc-menu',
        name: 'McccMenuPage',
        component: McccMenuPage
    },
    {
        path: '/history-admin',
        name: 'HistoryAdminPage',
        component: HistoryAdminPage
    },
    {
        path: '/supp-sheet',
        name: 'SuccessfullyDeletePage',
        component: SuccessfullyDeletedPage
    },
    {
        path: '/mccc-resource',
        name: 'McccResourcePage',
        component: McccResourcePage
    },
    {
        path: '/mccc-hours',
        name: 'McccFillHourPage',
        component: McccFillHourPage
    },
    {
        path: '/modif-saved',
        name: 'ModifSaved',
        component: ModifSaved
    },
    {
        path: '/mccc-teachers',
        name: 'McccTeachersPage',
        component: McccTeachersPage
    },
    {
        path: '/mccc-sae',
        name: 'McccSaePage',
        component: McccSAE
    },
    {
        path: '/mccc-skills',
        name: 'McccSkillsPage',
        component: McccSkills
    },
    {
        path: '/mccc-summary',
        name: 'McccSummaryPage',
        component: McccSummaryPage
    },
    {
        path: '/skill-creating',
        name: 'SkillsGestionPage',
        component: SkillsGestion
    },
    {
        path: '/resource-sheet-history',
        name: 'ResourceSheetHistory',
        component: ResourceSheetHistory
    },
    {
        path: '/resources-gestion',
        name: 'ResourcesGestion',
        component: ResourcesGestion
    },
    {
        path: '/users-gestion',
        name: 'UsersGestion',
        component: UsersGestion
    },
    {
        path: '/sae-gestion',
        name: 'SaeGestion',
        component: SaeGestion
    },
    {
        path: '/settings',
        name: 'Settings',
        component: Settings
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;