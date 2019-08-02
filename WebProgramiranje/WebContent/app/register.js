Vue.component("register", {
	data: function () {
		    return {
		      username: "",
		      password: "",
		      registerDiv: false,
		      newUser: {}
		    }
	},
	template: ` 
<div>
	<navbar></navbar>
	
	<div class="centered1">
	<div class="centered2">
	<br />
	<h2> Register</h2>
	<br />
	
	<table>
		<tr>
			<td><b>Username: </b></td>
			<td> <input type="text" v-model="newUser.username" ></td>
		</tr>
		<tr>
			<td><b>First Name: </b></td>
			<td> <input type="text" v-model="newUser.firstName" ></td>
		</tr>
		<tr>
			<td><b>Last Name: </b></td>
			<td> <input type="text" v-model="newUser.lastName" ></td>
		</tr>
		<tr>
			<td><b>Password: </b></td>
			<td> <input type="text" v-model="newUser.password" ></td>
		</tr>
		<tr>
			<td><b>Repeat Password: </b></td>
			<td> <input type="text" v-model="newUser.repeatPassword" ></td>
		</tr>
		<tr>
			<td><b>Phone Number: </b></td>
			<td> <input type="text" v-model="newUser.phoneNumber" ></td>
		</tr>
		<tr>
			<td><b>Email: </b></td>
			<td> <input type="text" v-model="newUser.email" ></td>
		</tr>
		<tr>
			<td><b>Picture: </b></td>
			<td> <input type="text" v-model="newUser.picture" ></td>
		</tr>
		<tr>
            <td>  </td>
            <td><button v-on:click="registerNewUser()">Register</button> </td>      
        </tr>
        </table
	</div>
	</div>

	
</div>		  
`
	, 
	methods : {
		registerNewUser: function() {
			if(this.newUser.username == "" || this.newUser.username == null) {
				toast("Enter username.");
				return;
			}
			if(this.newUser.firstName == "" || this.newUser.firstName == null) {
				toast("Enter first name.");
				return;
			}
			if(this.newUser.lastName == "" || this.newUser.lastName == null) {
				toast("Enter last name.");
				return;
			}
			if(this.newUser.email == "" || this.newUser.email == null) {
				toast("Enter email.");
				return;
			}
			if(this.newUser.phoneNumber == "" || this.newUser.phoneNumber == null) {
				toast("Enter phone number.");
				return;
			}
			if(this.newUser.password == "" || this.newUser.password == null) {
				toast("Enter password.");
				return;
			}
			if(this.newUser.repeatPassword == "" || this.newUser.repeatPassword == null) {
				toast("Repeat password.");
				return;
			}
			if(this.newUser.password != this.newUser.repeatPassword) {
				toast("Repeat password does not matches password.");
				return;
			}
			
			this.newUser.picture = "";
			
			axios
			.post('rest/server/register',this.newUser)
			.then(response => {
				toast(response.data);
				if(response.data.startsWith("Successfully")) {
					console.log("bravo");
					this.registerDiv = false;
				}
			});
		}
	},
});