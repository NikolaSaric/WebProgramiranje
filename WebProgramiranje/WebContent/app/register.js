Vue.component("register", {
	data: function () {
		    return {
		      username: "",
		      password: "",
		      registerDiv: false,
		      newUser: {},
		      imageData: ""
		    }
	},
	template: ` 
<div>
	<navbar></navbar>
	
	<div class="centered1">
	<div class="centered2">
	<br />
	<h2 class="h2"> Register</h2>
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
			<td>
			</td>
			<td v-if="imageData.length > 0">
				<img :src="imageData" width="200" height="300">
			</td>
		</tr>
		<tr>
			<td>
			</td>
			<td>
				<input type="file" @change="previewImage" accept="image/*">
			</td>
		</tr>
		<tr>
            <td>  </td>
            <td><button v-on:click="registerNewUser()" class="buttonG">Register</button> </td>      
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
			if(this.imageData == null || this.imageData == "") {
				toast("Enter profile picture.");
				return;
			}
			
			this.newUser.picture = this.imageData;
			
			axios
			.post('rest/server/register',this.newUser)
			.then(response => {
				toast(response.data);
				this.$router.push("/login");
			});
		},
		previewImage: function(event) {
            // Reference to the DOM input element
            var input = event.target;
            // Ensure that you have a file before attempting to read it
            if (input.files && input.files[0]) {
                // create a new FileReader to read this image and convert to base64 format
                var reader = new FileReader();
                // Define a callback function to run, when FileReader finishes its job
                reader.onload = (e) => {
                    // Note: arrow function used here, so that "this.imageData" refers to the imageData of Vue component
                    // Read image as base64 and set to imageData
                    this.imageData = e.target.result;
                }
                // Start the reader job - read file as a data url (base64 format)
                reader.readAsDataURL(input.files[0]);
            }
        }
	},
});