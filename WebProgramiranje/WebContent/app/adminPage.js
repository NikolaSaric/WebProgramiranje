Vue.component("adminPage", {
	data: function () {
		    return {
		      admin: {},
		      currentTab: 1
		    }
	},
	template: ` 
<div>
	<navbar></navbar>
	<div>
            <br>
             <div > 
                <h1>System Admin Profile</h1>
            </div>
             <br>
             <div >
                <ul >
                    <li >
                        <a href="#" @click="selectTab(1)">Profile</a>
                        
                    </li>
                    <li >
                        <a href="#" @click="selectTab(2)">Destinations</a>
                    </li>
                    <li >
                        <a  href="#" @click="selectTab(3)">Users</a>
                    </li>
                    <li>
                        <a  href="#" @click="selectTab(4)">Flights</a>
                    </li>
                    <li >
                        <a  href="#" @click="logOut()">Log Out</a>
                    </li>
                </ul>
            </div>

            <div  v-if="currentTab == 1" > 
            	<table>
            		<tr>
            			<td><b>Username: </b>
            			<td>{{admin.username}}</td>
            		</tr>
            		<tr>
            			<td><b>First Name: </b>
            			<td>{{admin.firstName}}</td>
            		</tr>
            		<tr>
            			<td><b>Last Name: </b>
            			<td>{{admin.lastName}}</td>
            		</tr>
            		<tr>
            			<td><b>Phone Number: </b>
            			<td>{{admin.phoneNumber}}</td>
            		</tr>
            		<tr>
            			<td><b>Email: </b>
            			<td>{{admin.email}}</td>
            		</tr>
            	</table>

            </div> 
            <div  v-if="currentTab == 2" >

            </div>
            <div  v-if="currentTab == 3" >
             
            </div>
            <div  v-if="currentTab == 4" >
           
            </div>
        </div>
	
	
</div>		  
`
	,
	mounted() {
		axios
		.get('rest/admin/getLoggedAdmin')
		.then(response => {
			this.admin = response.data;
		});
	},
	methods : {
		selectTab: function(tabId){
            this.currentTab = tabId;
        },
        logOut: function() {
        	axios
    		.get('rest/admin/logOut')
    		.then(response => {
    			toast(response.data);
    			window.location ="./";
    		});
        }
	},
});