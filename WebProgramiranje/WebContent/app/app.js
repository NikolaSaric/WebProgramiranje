const MainPage = { template: '<main-page></main-page>' }
const Login = { template: '<login></login>' }
const Register = { template: '<register></register>' }
const Navbar = { template: '<navbar></navbar>' }
const Profile = { template: '<profile></profile>' }
const AddDestination = { template: '<add-destination></add-destination>' }
const Destinations = { template: '<destinations></destinations>' }
const UsersAdmin = { template: '<users-admin></users-admin>' }
const AddFlight = { template: '<add-flight></add-flight>' }
const Flights = { template: '<flights></flights>' }
const SearchFlights = { template: '<search-flights></search-flights>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
		  { path: '/', component: MainPage },
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
	AddFlight,
	Flights,
	SearchFlights,
	el: '#flightReservations'
});

