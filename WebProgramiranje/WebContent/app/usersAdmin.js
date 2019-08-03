Vue.component("usersAdmin", {
	data: function () {
		    return {
		    	users: []
		    }
	},
	template: ` 
<div>
		<h3>All Users</h3>

	<table border="1">
		<tr>
			<th>Username</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Email</th>
			<th>Phone Number</th>
			<th>Blocked</th>
			<th>Options</th>
		</tr>
		<tr v-for="u in users">
			<td>{{u.username}}</td>
			<td>{{u.firstName}}</td>
			<td>{{u.lastName}}</td>
			<td>{{u.email}}</td>
			<td>{{u.phoneNumber}}</td>
			<td>{{u.blocked}}</td>
			<td v-if="u.blocked == false"><button v-on:click="block(u.username,u.blocked)">Block</button></td>
			<td v-if="u.blocked == true"><button v-on:click="block(u.username,u.blocked)">Unblock</button></td>
		</tr>
	</table>
</div>		  
`
	, 
	mounted() {
		
		axios.get("rest/admin/getAllUsers")
			.then(response => {
				this.users = response.data;
			});
	},
	methods : {
        block: function(username, blocked) {
        	var block = false;
        	if(blocked == true) {
        		block = false;
        	} else {
        		block = true;
        	}
        	axios.post("rest/admin/blockUser", {username:username,blocked:block})
			.then(response => {
				if(response.data == true) {
					if(block == true) {
						toast("Successfully blocked user.");
					} else {
						toast("Successfully unblocked user.");
					}
					
					this.users.forEach(function(u) {
						if(u.username === username) {
							u.blocked = block;
						}
					});
				} else {
					if(blocked == true) {
						block == false;
		        	} else {
		        		block == true;
		        	}
					
					if(block == true) {
						toast("There was a problem with blocking user.");
					} else {
						toast("There was a problem with unblocking user.");
					}
				}
			});
        }
	},
});