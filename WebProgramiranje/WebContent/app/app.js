const MainPage = { template: '<main-page></main-page>' }
const Login = { template: '<login></login>' }
const AdminPage = { template: '<admin-page></admin-page>' }
const Navbar = { template: '<navbar></navbar>' }
const Profile = { template: '<profile></profile>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
		  { path: '/', component: MainPage },
		  { path: '/adminPage', component: AdminPage },
		  { path: '/login', component: Login },
		  { path: '/profile', component: Profile }
	  ]
});

var app = new Vue({
	router,
	Navbar,
	el: '#flightReservations'
});

