const MainPage = { template: '<main-page></main-page>' }
const Login = { template: '<login></login>' }
const Register = { template: '<register></register>' }
const AdminPage = { template: '<admin-page></admin-page>' }
const Navbar = { template: '<navbar></navbar>' }
const Profile = { template: '<profile></profile>' }
const AddDestination = { template: '<add-destination></add-destination>' }
const Destinations = { template: '<destinations></destinations>' }
const UsersAdmin = { template: '<users-admin></users-admin>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
		  { path: '/', component: MainPage },
		  { path: '/adminPage', component: AdminPage },
		  { path: '/login', component: Login },
		  { path: '/register', component: Register },
		  { path: '/profile', component: Profile }
	  ]
});

var app = new Vue({
	router,
	Navbar,
	AddDestination,
	Destinations,
	UsersAdmin,
	el: '#flightReservations'
});

