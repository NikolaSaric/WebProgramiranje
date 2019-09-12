Vue.component("addDestination", {
	data: function () {
		    return {
		    	destination: {},
		    	imageData: ""
		    }
	},
	template: ` 
<div>
	<h3 class="h3">Add New Destination</h3>

	<table>
		<tr>
			<td><b>Enter Name: </b></td>
			<td><input type="text" v-model="destination.name" ></td>
		</tr>
		<tr>
			<td><b>Enter Country: </b></td>
			<td><input type="text" v-model="destination.country" ></td>
		</tr>
		<tr>
			<td><b>Enter Airport: </b></td>
			<td><input type="text" v-model="destination.airport" ></td>
		</tr>
		<tr>
			<td><b>Enter Airport Code: </b></td>
			<td><input type="text" v-model="destination.airportCode" ></td>
		</tr>
		<tr>
			<td><b>Enter Coordinates: </b></td>
			<td><input type="text" v-model="destination.coordinates" ></td>
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
			<td></td>
			<td><button v-on:click="addDestination()" class="buttonB">Add Destination</button></td>
		</tr>
	</table>
	
</div>		  
`
	, 

	methods : {
        addDestination: function() {
        	this.destination.active = true;
        	
        	if(this.destination.name === null || this.destination.name === "") {
        		toast("Enter destination name.");
        		return;
        	}
        	if(this.destination.country === null || this.destination.country === "") {
        		toast("Enter destination country.");
        		return;
        	}
        	if(this.destination.airport === null || this.destination.airport === "") {
        		toast("Enter destination airport.");
        		return;
        	}
        	if(this.destination.airportCode === null || this.destination.airportCode === "") {
        		toast("Enter destination airport code.");
        		return;
        	}
        	if(this.destination.coordinates === null || this.destination.coordinates === "") {
        		toast("Enter destination coordinates.");
        		return;
        	}
        	if(this.imageData === null || this.imageData === "") {
        		toast("Enter destination picture.");
        		return;
        	}
        	this.destination.picture = this.imageData;
        	
        	axios.post("rest/destination/addDestination", this.destination)
        		.then(response => {
        			toast(response.data);
        			var dests = JSON.parse(localStorage.destinations);
        			dests.push(this.destination);
        			localStorage.destinations = JSON.stringify(dests);
        			this.destination = {};
        			this.imageData = "";
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