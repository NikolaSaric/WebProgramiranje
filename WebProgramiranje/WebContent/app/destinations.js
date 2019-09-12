Vue.component("destinations", {
	data: function () {
		    return {
		    	destinations: [],
		    	editDestination: false,
		    	editingDestination: {},
		    	oldDestination: {},
		    	imageData: ""
		    	
		    }
	},
	template: ` 
<div>
	<div v-if="editDestination == false">
	<h3 class="h3">All Destinations</h3>

	<table class="table">
		<tr>
			<th>Name</th>
			<th>Country</th>
			<th>Airport</th>
			<th>Code</th>
			<th>Coordinates</th>
			<th>Active</th>
			<th>Options</th>
		</tr>
		<tr v-for="d in destinations">
			<td>{{d.name}}</td>
			<td>{{d.country}}</td>
			<td>{{d.airport}}</td>
			<td>{{d.airportCode}}</td>
			<td>{{d.coordinates}}</td>
			<td>{{d.active}}</td>
			<td v-if="d.active==true"><button v-on:click="deactivate(d.name,d.country)" class="buttonR">Deactivate</button></td>
			<td v-if="d.active==false"><button v-on:click="activate(d.name,d.country)" class="buttonG">Activate</button></td>
			<td><button v-on:click="edit(d)" class="buttonB">Edit</button></td>
		</tr>
	</table>
	
	</div>		  
	
	<div v-if="editDestination == true">
		<h3>Edit Destination</h3>
		<table>
			<tr>
				<td><b>Name: </b></td>
				<td>{{editingDestination.name}}</td>
			</tr>
			<tr>
				<td><b>Country: </b></td>
				<td>{{editingDestination.country}}</td>
			</tr>
			<tr>
				<td><b>Active: </b></td>
				<td>{{editingDestination.active}}</td>
			</tr>
			<tr>
				<td><b>Enter Airport: </b></td>
				<td><input type="text" v-model="editingDestination.airport"></td>
			</tr>
			<tr>
				<td><b>Enter Airport Code: </b></td>
				<td><input type="text" v-model="editingDestination.airportCode"></td>
			</tr>
			<tr>
				<td><b>Enter Coordinates: </b></td>
				<td><input type="text" v-model="editingDestination.coordinates"></td>
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
				<td><button v-on:click="saveEditing()" class="buttonG">Save</button></td>
				<td><button v-on:click="cancelEditing()" class="buttonR">Cancel</button></td>
			</tr>
		</table>
	</div>
</div>
`
	, 
	mounted() {
		if(localStorage.destinations === "") {
			axios.get("rest/destination/getAllDestinations")
				.then(response => {
					localStorage.destinations = JSON.stringify(response.data);
					this.destinations = response.data;
				});
		} else {
			this.destinations = JSON.parse(localStorage.destinations);
		}
		
	},
	methods : {
		activate: function(name, country) {
			if(name == null || name == "") {
				return;
			}
			if(country == null || country == "") {
				return;
			}
			
			axios.post("rest/destination/activateDestination", {name:name,country:country})
				.then(response => {
					if(response.data == true) {
						toast("Successfully activated destination.");
						this.destinations.forEach(function(d) {
							if(d.name === name && d.country === country) {
								d.active = true;
							}
						});
						localStorage.destinations = JSON.stringify(this.destinations);
					} else {
						toast("There was an error with activating destination.");
					}
				});
		},
		deactivate: function(name, country) {
			if(name == null || name == "") {
				return;
			}
			if(country == null || country == "") {
				return;
			}
			
			axios.post("rest/destination/deactivateDestination", {name:name,country:country})
				.then(response => {
					if(response.data == true) {
						toast("Successfully deactivated destination.");
						this.destinations.forEach(function(d) {
							if(d.name === name && d.country === country) {
								d.active = false;
							}
						});
						localStorage.destinations = JSON.stringify(this.destinations);
					} else {
						toast("There was an error with deactivating destination.");
					}
				});
		},
		edit: function(dest) {
			this.editingDestination = dest;
			this.oldDestination.airport = dest.airport;
			this.oldDestination.airportCode = dest.airportCode;
			this.oldDestination.coordinates = dest.coordinates;
			this.imageData = dest.picture
			this.oldDestination.picture = dest.picture;
			this.editDestination = true;
		},
		cancelEditing: function() {
			this.editingDestination.airport = this.oldDestination.airport;
			this.editingDestination.airportCode = this.oldDestination.airportCode;
			this.editingDestination.coordinates = this.oldDestination.coordinates;
			this.editingDestination.picture = this.oldDestination.picture;
			this.editDestination = false;
		},
		saveEditing: function() {
			if(this.editingDestination.airport === null || this.editingDestination.airport === "") {
        		return "Enter destination airport.";
        	}
        	if(this.editingDestination.airportCode === null || this.editingDestination.airportCode === "") {
        		return "Enter destination airport code.";
        	}
        	if(this.editingDestination.coordinates === null || this.editingDestination.coordinates === "") {
        		return "Enter destination coordinates.";
        	}
        	if(this.imageData === null || this.imageData === "") {
        		return "Enter destination picture.";
        	}
        	this.editingDestination.picture = this.imageData;
        	
        	var name = this.editingDestination.name;
        	var country = this.editingDestination.country;
        	axios.post("rest/destination/editDestination", this.editingDestination)
			.then(response => {
				if(response.data == true) {
					toast("Successfully edited destination.");
					this.destinations.forEach(function(d) {
						if(d.name === name && d.country === country) {
							d = this.editingDestination;
						}
					});
					localStorage.destinations = JSON.stringify(this.destinations);
					this.editDestination = false;
				} else {
					toast("There was an error with editing destination.");
				}
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