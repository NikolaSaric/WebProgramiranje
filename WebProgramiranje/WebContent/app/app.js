const MainPage = { template: '<main-page></main-page>' }
const Login = { template: '<login></login>' }
const AdminPage = { template: '<admin-page></admin-page>' }
const Navbar = { template: '<navbar></navbar>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
		  { path: '/', component: MainPage },
		  { path: '/adminPage', component: AdminPage },
		  { path: '/login', component: Login }
	  ]
});

var app = new Vue({
	router,
	Navbar,
	el: '#flightReservations'
});

