Vue.component("addDestination", {
	data: function () {
		    return {
		    	destination: {}
		    }
	},
	template: ` 
<div>
	<h3>Add New Destination</h3>

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
			<td></td>
			<td><button v-on:click="addDestination()">Add Destination</button></td>
		</tr>
	</table>
	
</div>		  
`
	, 

	methods : {
        addDestination: function() {
        	this.destination.picture = "Test picture.";
        	this.destination.active = true;
        	
        	if(this.destination.name === null || this.destination.name === "") {
        		return "Enter destination name.";
        	}
        	if(this.destination.country === null || this.destination.country === "") {
        		return "Enter destination country.";
        	}
        	if(this.destination.airport === null || this.destination.airport === "") {
        		return "Enter destination airport.";
        	}
        	if(this.destination.airportCode === null || this.destination.airportCode === "") {
        		return "Enter destination airport code.";
        	}
        	if(this.destination.coordinates === null || this.destination.coordinates === "") {
        		return "Enter destination coordinates.";
        	}
        	if(this.destination.picture === null || this.destination.picture === "") {
        		return "Enter destination picture.";
        	}
        	
        	axios.post("rest/admin/addDestination", this.destination)
        		.then(response => {
        			toast(response.data);
        			var dests = JSON.parse(localStorage.destinations);
        			dests.push(this.destination);
        			localStorage.destinations = JSON.stringify(dests);
        			this.destination = {};
        		});
        }
	},
});