Vue.component("login", {
	data: function () {
		    return {
		      username: "",
		      password: ""
		    }
	},
	template: ` 
<div>
	<navbar></navbar>
	
	<div class="centered1">
	<div class="centered2">
	<br />
	<h2> Login</h2>
	<br />
	<table>
		<tr>
			<td><b>Username: </b></td>
			<td> <input type="text" v-model="username" ></td>
		</tr>
		<tr>
			<td><b>Passsword: </b></td>
			<td> <input type="text" v-model="password" ></td>
		</tr>
		<tr>
            <td>  </td>
            <td><button v-on:click="login()">Login</button> </td>      
        </tr>
	</table>
	
	</div>
	</div>

	
</div>		  
`
	, 
	methods : {
		login : function () {
			if(this.username == "") {
				toast("Enter username.");
			}
			if(this.password == "") {
				toast("Enter password.");
			}
			
			axios
			.post('rest/server/login',{username: this.username, password: this.password})
			.then(response => {
				if(response.data !== null) {
					toast("Logged in as: " + response.data.username + response.data.role);
					localStorage.loggedUsername = response.data.username;
					localStorage.loggedFirstName = response.data.firstName;
					localStorage.loggedLastName = response.data.lastName;
					localStorage.loggedEmail = response.data.email;
					localStorage.loggedPhoneNumber = response.data.phoneNumber;
					localStorage.loggedBlocked = response.data.blocked;
					localStorage.loggedRole = response.data.role;
					this.$router.push("/");
				} else {
					toast("Username or password are incorrect.");
					localStorage.setItem("loggedUser","");
					return;
				}
				
			});
			axios.get("rest/admin/getAllDestinations")
			.then(response => {
				localStorage.destinations = JSON.stringify(response.data);
			});
		}
	},
});