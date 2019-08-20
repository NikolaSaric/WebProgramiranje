Vue.component("addFlight", {
	data: function () {
		    return {
		    	flight: {},
		    	destinations: [],
		    	startFlight: "",
		    	arrivalFlight: "",
		    	flightDate: ""
		    }
	},
	template: ` 
<div>
	<h3>Add New Flight</h3>

	<table>
		<tr>
			<td><b>Enter Flight Number: </b></td>
			<td><input type="text" v-model="flight.number" ></td>
		</tr>
		<tr>
			<td><b>Enter Starting Destination: </b></td>
			<td>
				<select v-model="startFlight">
					<option v-for="dest in destinations" :value="dest.name +'|'+dest.country">{{dest.name}}, {{dest.country}}</option>
				</select>
			</td>
		</tr>
		<tr>
			<td><b>Enter Arrival Destination: </b></td>
			<td>
				<select v-model="arrivalFlight">
					<option v-for="dest in destinations" :value="dest.name +'|'+dest.country">{{dest.name}}, {{dest.country}}</option>
				</select>
			</td>
		</tr>
		<tr>
			<td><b>Enter Price: </b></td>
			<td><input type="number" v-model="flight.price" ></td>
		</tr>
		<tr>
			<td><b>Enter Plane Model: </b></td>
			<td><input type="text" v-model="flight.planeModel" ></td>
		</tr>
		<tr>
			<td><b>Enter First Class Seats: </b></td>
			<td><input type="number" v-model="flight.firstClass" ></td>
		</tr>
		<tr>
			<td><b>Enter Business Class Seats: </b></td>
			<td><input type="number" v-model="flight.businessClass" ></td>
		</tr>
		<tr>
			<td><b>Enter Economic Class Seats: </b></td>
			<td><input type="number" v-model="flight.ecoClass" ></td>
		</tr>
		<tr>
			<td><b>Enter Date: </b></td>
			<td><input type="date" v-model="flightDate" ></td>
		</tr>
		<tr>
			<td><b>Enter Flight Class: </b></td>
			<td>
				<select v-model="flight.flightClass">
					<option value="0">Charter</option>
					<option value="1">Regional</option>
					<option value="2">Transoceanic</option>
				</select>
			</td>
		</tr>
		<tr>
			<td></td>
			<td><button v-on:click="addFlight()">Add Flight</button></td>
		</tr>
	</table>
	
</div>		  
`
	, 
	mounted() {
		this.destinations = JSON.parse(localStorage.destinations);
	},

	methods : {
        addFlight: function() {
        	var startDestination = this.startFlight.split("|");
        	var arrivalDestination = this.arrivalFlight.split("|");
        	this.flight.startDestinationName = startDestination[0];
        	this.flight.startDestinationCountry = startDestination[1];
        	this.flight.arrivalDestinationName = arrivalDestination[0];
        	this.flight.arrivalDestinationCountry = arrivalDestination[1];
        	
        	var date = this.flightDate.split("-");
        	this.flight.date = date[2] + "/" + date[1] + "/" + date[0];
        	
        	if(this.number === null || this.number === "") {
        		toast("Enter flight number.");
        		return;
        	}
        	if(this.flight.startDestinationName === null || this.flight.startDestinationName === "") {
        		toast("Enter starting destination name.");
        		return;
        	}
        	if(this.flight.startDestinationCountry === null || this.flight.startDestinationCountry === "") {
        		toast("Enter starting destination country.");
        		return;
        	}
        	if(this.flight.arrivalDestinationName === null || this.flight.arrivalDestinationName === "") {
        		toast("Enter arrival destination name.");
        		return;
        	}
        	if(this.flight.arrivalDestinationCountry === null || this.flight.arrivalDestinationCountry === "") {
        		toast("Enter arrival country.");
        		return;
        	}
        	if(this.flight.price === null || this.flight.price < 0) {
        		toast("Enter price, must be >= than 0.");
        		return;
        	}
        	if(this.flight.planeModel === null || this.flight.planeModel === "") {
        		toast("Enter plane model.");
        		return;
        	}
        	if(this.flight.firstClass === null || this.flight.firstClass < 0) {
        		toast("Enter number of first class seats, must be >= 0.");
        		return;
        	}
        	if(this.flight.businessClass === null || this.flight.businessClass < 0) {
        		toast("Enter number of business class seats, must be >= 0.");
        		return;
        	}
        	if(this.flight.ecoClass === null || this.flight.ecoClass < 0) {
        		toast("Enter number of economic class seats, must be >= 0.");
        		return;
        	}
        	if(this.flight.date === null) {
        		toast("Enter flight date.");
        		return;
        	}
        	if(this.flight.flightClass === null) {
        		toast("Choose flight class.");
        		return;
        	}
        	
        	
        	
        	axios.post("rest/flight/addFlight", this.flight)
        		.then(response => {
        			if(response.data == true) {
        				toast("Successfully added new flight.");
        				this.flight = {};
        				this.startFlight = "";
        				this.arrivalFlight = "";
        				this.flightDate ="";
        			} else {
        				toast(response.data);
        			}
        			
        		});
        }
	},
});