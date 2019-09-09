Vue.component("mainPage", {
	data: function () {
		    return {
		    	userRole: 0,
		    	currentTab: 0
		    }
	},
	template: ` 
<div>
	<navbar></navbar>
	<div class="centered1">
	<div class="centered2">
		<div v-if="userRole==1">
		<h2>Regular User Page</h2>
		<div class="userMenu">
			<ul>
				<li>
					<a  href="#" @click="selectTab(1)">Search Flights</a>
				</li>
				<li>
					<a  href="#" @click="selectTab(2)">Reservations</a>
				</li>
			</ul>
		</div>
		<br />
			<div  v-if="currentTab == 1"> 
                <search-flights></search-flights>
            </div> 
            <div  v-if="currentTab == 2">
                <h3>Reservations</h3>
            </div>

		</div>
		<div v-if="userRole==2">
		<h2>Admin Main Page</h2>
		<div class="userMenu">
			<ul>
				<li>
					<a  href="#" @click="selectTab(1)">Destinations</a>
				</li>
				<li>
					<a  href="#" @click="selectTab(2)">Add Destination</a>
				</li>
				<li>
					<a  href="#" @click="selectTab(3)">Users</a>
				</li>
				<li>
					<a  href="#" @click="selectTab(4)">Flights</a>
				</li>
				<li>
					<a  href="#" @click="selectTab(5)">Add Flight</a>
				</li>
			</ul>
		</div>
		<br />
			<div  v-if="currentTab == 1"> 
                <destinations></destinations>
            </div> 
            <div  v-if="currentTab == 2">
                <add-destination></add-destination>
            </div>
            <div  v-if="currentTab == 3">
                <users-admin></users-admin>
            </div>
            <div  v-if="currentTab == 4">
            	<flights></flights>
                
            </div>
            <div  v-if="currentTab == 5">
            	<add-flight></add-flight>
            </div>
		</div>
	</div>
	</div>
	
</div>		  
`
	, 
	mounted() {
		if(localStorage.loggedRole === "RegularUser") {
			this.userRole = 1;
		} else if(localStorage.loggedRole === "Admin") {
			this.userRole = 2;
		}
	},
	methods : {
        selectTab: function(tabId){
            this.currentTab = tabId;
        }
	},
});