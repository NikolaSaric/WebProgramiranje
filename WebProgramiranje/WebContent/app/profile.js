Vue.component("profile", {
	data: function () {
		    return {
		    	username: "",
		    	firstName: "",
		    	lastName: "",
		    	email: "",
		    	phoneNumber: "",
		    	blocked: "",
		    	
		    	oldFirstName: "",
		    	oldLastName: "",
		    	oldEmail: "",
		    	oldPhoneNumber: "",
		    	
		    	oldPassword: "",
		    	newPassword: "",
		    	repeatPassword: "",
		    	editPassword: false,
		    	
		    	edit: false
		    }
	},
	template: ` 
<div>
	<navbar></navbar>
	
	<div class="centered1">
	<div class="centered2">
		<br />
		<h2>User Profile</h2>
		<br />
		<table>
			<tr>
				<td><b>Username: </b></td>
				<td>{{username}}</td>
			</tr>
			<tr>
				<td><b>Blocked: </b></td>
				<td>{{blocked}}</td>
			</tr>
		</table>
		<br />
		
		<table v-if="!editPassword">
			<tr>
				<td><b>First Name: </b></td>
				<td><input type="text" v-model="firstName" v-bind:disabled="edit==false"></td>
			</tr>
			<tr>
				<td><b>Last Name: </b></td>
				<td><input type="text" v-model="lastName" v-bind:disabled="edit==false"></td>
			</tr>
			<tr v-if="!editPassword">
				<td><b>Email: </b></td>
				<td><input type="text" v-model="email" v-bind:disabled="edit==false"></td>
			</tr>
			<tr>
				<td><b>Phone Number: </b></td>
				<td><input type="text" v-model="phoneNumber" v-bind:disabled="edit==false"></td>
			</tr>
			<tr v-if="!edit">
				<td></td>
				<td>
				<button v-on:click="editProfile()">Edit</button>
				<button v-on:click="changePassword()">Change Password</button>
				</td>
			</tr>
			<tr v-if="edit">
				<td></td>
				<td>
				<button v-on:click="saveProfile()">Save</button>
				<button v-on:click="cancel()">Cancel</button>
				</td>
			</tr>
			
		</table>
		
		<table v-if="editPassword">
			<tr>
				<td><b>Old Password: </b></td>
				<td><input type="password" v-model="oldPassword"></td>
			<tr>
			<tr>
				<td><b>New Password: </b></td>
				<td><input type="password" v-model="newPassword"></td>
			<tr>
			<tr>
				<td><b>Repeat Password: </b></td>
				<td><input type="password" v-model="repeatPassword"></td>
			<tr>
			<tr>
				<td></td>
				<td>
				<button v-on:click="savePassword()">Save</button>
				<button v-on:click="cancelPassword()">Cancel</button>
				</td>
			</tr>
		</table>
	
	</div>
	</div>	
</div>	  
`
	,
	mounted() {
		this.username = localStorage.loggedUsername;
		this.firstName = localStorage.loggedFirstName;
		this.lastName = localStorage.loggedLastName;
		this.email = localStorage.loggedEmail;
		this.phoneNumber = localStorage.loggedPhoneNumber;
		this.blocked = localStorage.loggedBlocked;
	},
	methods : {
		editProfile: function() {
			this.oldFirstName = this.firstName;
			this.oldLastName = this.lastName;
			this.oldEmail = this.email;
			this.oldPhoneNumber = this.phoneNumber;
			
			this.edit = true;
		},
		saveProfile: function() {
			axios.post("rest/server/editProfile",{firstName:this.firstName,lastName:this.lastName,email:this.email,phoneNumber:this.phoneNumber})
				.then(response => {
					toast(response.data);
				});
			
			this.edit = false;
		},
		cancel: function() {
			this.firstName = this.oldFirstName;
			this.lastName = this.oldLastName;
			this.email = this.oldEmail;
			this.phoneNumber = this.oldPhoneNumber;
			
			this.edit = false;
		},
		changePassword: function() {
			this.editPassword = true;
		},
		cancelPassword: function() {
			this.oldPassword = "";
			this.newPassword = "";
			this.repeatPassword = "";
			
			this.editPassword = false;
		},
		savePassword: function() {
			if(this.oldPassword == "") {
				toast("Enter old password.");
				return;
			}
			if(this.newPassword == "") {
				toast("Enter new password.");
				return;
			}
			if(this.repeatPassword == "") {
				toast("Repeat new password.");
				return;
			}
			if(this.newPassword !== this.repeatPassword) {
				toast("Repeated password does not match new password.");
				return;
			}
			
			axios.post("rest/server/changePassword",{oldPassword:this.oldPassword,newPassword:this.newPassword,repeatedPassword:this.repeatPassword})
				.then(response => {
					toast(response.data);
				});
			
			this.cancelPassword();
		}
	},
});