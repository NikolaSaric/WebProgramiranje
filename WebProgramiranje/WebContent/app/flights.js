Vue.component("flights", {
	data: function () {
		    return {
		    	flights: [],
		    	editFlight: false,
		    	editingFlight: {},
		    	oldFlight: {}
		    	
		    }
	},
	template: ` 
<div>
	<div v-if="editFlight == false">
	<h3>All Flights</h3>

	<table border="1">
		<tr>
			<th>Number</th>
			<th>Starting Destination</th>
			<th>Arrival Destination</th>
			<th>Date</th>
			<th>Plane Model</th>
			<th>Flight Class</th>
			<th>Price</th>
			<th>First Class</th>
			<th>Business Class</th>
			<th>Economy Class</th>
			<th>Sold Tickets</th>
			<th>Options</th>
		</tr>
		<tr v-for="f in flights">
			<td>{{f.number}}</td>
			<td>{{f.startDestination}}</td>
			<td>{{f.arrivalDestination}}</td>
			<td>{{f.date}}</td>
			<td>{{f.planeModel}}</td>
			<td>{{f.flightClass}}</td>
			<td>{{f.price}}</td>
			<td>{{f.firstClass}}</td>
			<td>{{f.businessClass}}</td>
			<td>{{f.ecoClass}}</td>
			<td>{{f.soldTickets}}</td>
			<td><button v-on:click="activate(f.number)">Delete</button></td>
			<td><button v-on:click="edit(f)">Edit</button></td>
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
				<td><button v-on:click="saveEditing()">Save</button></td>
				<td><button v-on:click="cancelEditing()">Cancel</button></td>
			</tr>
		</table>
	</div>
</div>
`
	, 
	mounted() {
		axios("rest/flight/getAllFlights")
			.then(response => {
				this.flights = response.data;
			});
		
	},
	methods : {
		edit: function(dest) {
			this.editingDestination = dest;
			this.oldDestination.airport = dest.airport;
			this.oldDestination.airportCode = dest.airportCode;
			this.oldDestination.coordinates = dest.coordinates;
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
        	if(this.editingDestination.picture === null || this.editingDestination.picture === "") {
        		return "Enter destination picture.";
        	}

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
		}
	},
});