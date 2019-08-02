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
			</ul>
		</div>
		<br />
			<div  v-if="currentTab == 1"> 
                <destinations></destinations>
            </div> 
            <div  v-if="currentTab == 2">
                <add-destination></add-destination>
            </div>
		</div>
	</div>
	</div>
	
</div>		  
`
	, 
	mounted() {
		
		if(localStorage.role === "Regular") {
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