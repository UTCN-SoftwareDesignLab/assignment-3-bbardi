import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import UserAdmin from "../views/UserAdmin"
import PatientsManagement from "../views/PatientsManagement"
import Consultations from '../views/Consultations'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/users',
    name: 'UserAdmin',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: UserAdmin
  },
  {
    path: '/patients',
    name: 'PatientManagement',
    component: PatientsManagement,
  },
  {
    path: '/consult',
    name: "Consultations",
    component: Consultations,
  },
]

const router = new VueRouter({
  routes
})

export default router