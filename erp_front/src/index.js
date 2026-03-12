import  {createRouter, createWebHistory} from 'vue-router';
import LoginPage from './components/App/LoginPage.vue';
import ResourcePage from '@/components/ResourceSheet/ResourcePage.vue';
import HomePage from './components/App/HomePage.vue';
import HistoryPage from '@/components/ResourceSheet/HistoryPage.vue';
import CreateNewUserPage from './components/Admin/CreateNewUserPage.vue';
import HelpPage from './components/App/HelpPage.vue';
import FillResourceSheet from "@/components/ResourceSheet/FillResourceSheet.vue";
import HistoryAdminPage from "./components/Admin/HistoryAdminPage.vue";
import McccResourcePage from "./components/Mccc/McccResourcePage.vue";
import McccFillHourPage from "./components/Mccc/McccFillHour.vue";
import McccMenuPage from "./components/Mccc/McccMenuPage.vue";
import McccTeachersPage from "./components/Mccc/McccTeachersPage.vue";
import McccSAE from "./components/Mccc/McccSAE.vue";
import McccSkills from "./components/Mccc/McccSkills.vue";
import McccSummaryPage from "./components/Mccc/McccSummary.vue";
import SkillsManagement from "./components/Admin/SkillsManagement.vue";
import ResourceSheetHistory from "@/components/ResourceSheet/ResourceSheetHistory.vue";
import ResourcesManagement from "./components/Admin/ResourcesManagement.vue";
import UsersManagement from "./components/Admin/UsersManagement.vue";
import SaeManagement from "./components/Admin/SaeManagement.vue";
import Settings from "./components/App/Settings.vue";
import LogoutModal from "./components/Information/LogoutModal.vue";
import RoleManagement from "@/components/Admin/RoleManagement.vue";
import SheetsToValidate from "@/components/ResourceSheet/SheetsToValidate.vue";
import ResourceSheetToValidate from "@/components/ResourceSheet/ResourceSheetToValidate.vue";
import RecallManagement from "@/components/Admin/RecallManagement.vue";
import AuthSuccess from "@/components/App/AuthSuccess.vue";


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
        path: '/fill-resource-sheet',
        name: 'FillResourceSheet',
        component: FillResourceSheet
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
        component: SkillsManagement
    },
    {
        path: '/resource-sheet-history',
        name: 'ResourceSheetHistory',
        component: ResourceSheetHistory
    },
    {
        path: '/resources-management',
        name: 'ResourcesGestion',
        component: ResourcesManagement
    },
    {
        path: '/users-management',
        name: 'UsersGestion',
        component: UsersManagement
    },
    {
        path: '/sae-management',
        name: 'SaeGestion',
        component: SaeManagement
    },
    {
        path: '/settings',
        name: 'Settings',
        component: Settings
    },
    {
        path:'/logout',
        name: 'LogoutModal',
        component: LogoutModal
    },
    {
        path:'/role-management',
        name: RoleManagement,
        component: RoleManagement
    },
    {
        path:'/to-validate',
        name: SheetsToValidate,
        component: SheetsToValidate
    },
    {
        path:'/resource-sheet-to-validate',
        name: ResourceSheetToValidate,
        component: ResourceSheetToValidate
    },
    {
        path:'/recall-management',
        name: RecallManagement,
        component: RecallManagement
    },
    {
        path: '/success',
        name: 'Success',
        component: AuthSuccess
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;