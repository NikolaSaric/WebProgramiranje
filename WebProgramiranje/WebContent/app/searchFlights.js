Vue.component("searchFlights", {
	data: function () {
		    return {
		    	flights: [],
		    	tableFlights: [],
		    	destinations: [],
		    	startCountry: "",
		    	arrivalCountry: "",
		    	startAirport: "",
		    	arrivalAirport: "",
		    	firstDate: "",
		    	countries: [],
		    	startAvailableAirports: [],
		    	arrivalAvailableAirports: [],
		    	showFlights: false,
		    	filterDate: 0,
		    	filterClass: 3,
		    	filterNumber: "",
		    	foundFlights: []
		    }
	},
	template: ` 
<div>
	<div class="centered1">
	<div class="centered2">
	<h3>Search Flights</h3>
	<table>
		<tr>
			<td>
				<table>
		<tr>
			<td><b>Enter Starting Country: </b></td>
			<td>
				<select v-model="startCountry">
					<option v-for="c in countries" :value="c">{{c}}</option>
				</select>
			</td>
			<td><b>Airport: </b></td>
			<td>
				<select v-model="startAirport">
					<option v-for="a in startAvailableAirports" :value="a">{{a}}</option>
				</select>
			</td>
		</tr>
		<tr>
			<td><b>Enter Arrival Country: </b></td>
			<td>
				<select v-model="arrivalCountry">
					<option v-for="c in countries" :value="c">{{c}}</option>
				</select>
			</td>
			<td><b>Airport: </b></td>
			<td>
				<select v-model="arrivalAirport">
					<option v-for="a in arrivalAvailableAirports" :value="a">{{a}}</option>
				</select>
			</td>
		</tr>
		<tr>
			<td><b>Enter Date: </b></td>
			<td><input type="date" v-model="firstDate" ></td>
		</tr>
		<tr>
			<td></td>
			<td><button v-on:click="searchFlights()">Search Flights</button></td>
			<td><button v-on:click="reset()">Reset</button></td>
		</tr>
	</table>
			</td>
			<td v-if="showFlights">
				<table>
					<tr>
						<td><b>Date</b></td>
						<td>
							<select v-model="filterDate">
								<option value="0"></option>
								<option value="1">First</option>
								<option value="2">Last</option>
							</select>
						</td>
					<tr>
					<tr>
						<td><b>Class</b></td>
						<td>
							<select v-model="filterClass">
								<option value="3"></option>
								<option value="0">Charter</option>
								<option value="1">Regional</option>
								<option value="2">Oceanic</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><b>Flight Number</b></td>
						<td>
							<input type="text" v-model="filterNumber">
						</td>
					</tr>
					<tr>
							<td></td>
							<td><button v-on:click="filterFlights()">Filter Flights</button></td>
							<td><button v-on:click="resetFilter()">Reset</button></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

	
	<br />
	<table border="1" v-if="showFlights">
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
		<tr v-for="(f,index) in tableFlights">
			<td>{{f.number}}</td>
			<td>{{f.startDestination}}</td>
			<td>{{f.arrivalDestination}}</td>
			<td>{{new Date(f.date)}}</td>
			<td>{{f.planeModel}}</td>
			<td>{{f.flightClass}}</td>
			<td>{{f.price}}</td>
			<td>{{f.firstClass}}</td>
			<td>{{f.businessClass}}</td>
			<td>{{f.ecoClass}}</td>
			<td>{{f.soldTickets}}</td>
			<td><button v-on:click="">Details</button></td>
		</tr>
	</table>
	</div>
	</div>
</div>
`
	, 
	mounted() {
		this.destinations = JSON.parse(localStorage.destinations);

		this.countries.push("");
		for (var i = 0, len = this.destinations.length; i < len; i++) {
			  if(!this.countries.includes(this.destinations[i].country)) {
				  this.countries.push(this.destinations[i].country);
			  }
			}
		
		//this.firstDate = new Date();
	},
	methods : {
		searchFlights: function() {
			if(this.firstDate == null || this.firstDate == "") {
				this.firstDate = new Date();
			}
			
			axios.post("rest/flight/searchFlights", {startCountry: this.startCountry, arrivalCountry: this.arrivalCountry,startAirport: this.startAirport, arrivalAirport: this.arrivalAirport,date: this.firstDate})
				.then(response => {
					this.flights = response.data;
					this.tableFlights = response.data;
				});
			this.showFlights = true;
		},
		reset: function() {
			this.firstDate = new Date();
			this.startCountry = "";
			this.arrivalCountry = "";
			this.showFlights = false;
		},
		filterFlights: function() {
			if(this.filterNumber !== "") {
				for(var i = 0, len = this.flights.length; i < len; i++) {
					if(this.flights[i].number == this.filterNumber) {
						this.tableFlights = [];
						this.tableFlights.push(this.flights[i]);
						return;
					}
				}
				toast("No flight with given number found.");
			}
			if(this.filterClass == 3) {
				this.tableFlights = this.flights;
			}
			if(this.filterClass != 3) {
				this.tableFlights = [];
				this.foundFlights = [];
				for(var i = 0, len = this.flights.length; i < len; i++) {
					if(this.flights[i].flightClass == this.filterClass) {
						this.foundFlights.push(this.flights[i]);
					}
				}
				this.tableFlights = this.foundFlights;
				
				if(this.filterDate == 1) {
					this.tableFlights = this.foundFlights.slice().sort((a,b) => a.date - b.date)
				} else if(this.filterDate == 2) {
					this.tableFlights = this.foundFlights.slice().sort((a,b) => b.date - a.date)
				}
				
				return;
			}
			if(this.filterDate == 1) {
				this.tableFlights = this.flights.slice().sort((a,b) => a.date - b.date)
			} else if(this.filterDate == 2) {
				this.tableFlights = this.flights.slice().sort((a,b) => b.date - a.date)
			}
		},
		resetFilter: function() {
			this.filterClass = 3;
			this.filterDate = 0;
			this.filterNumber = "";
			this.tableFlights = this.flights;
		}
	},
	watch: {
	    startCountry: function() {
	      this.startAirport = "";
	      this.startAvailableAirports = [];
	      this.startAvailableAirports.push("");
	      for (var i = 0, len = this.destinations.length; i < len; i++) {
			  if(this.startCountry == this.destinations[i].country && !this.startAvailableAirports.includes(this.destinations[i].name)) {
				  this.startAvailableAirports.push(this.destinations[i].name);
			  }
			}
	    },
	    arrivalCountry: function() {
		      this.arrivalAirport = "";
		      this.arrivalAvailableAirports = [];
		      this.arrivalAvailableAirports.push("");
		      for (var i = 0, len = this.destinations.length; i < len; i++) {
				  if(this.arrivalCountry == this.destinations[i].country && !this.arrivalAvailableAirports.includes(this.destinations[i].name)) {
					  this.arrivalAvailableAirports.push(this.destinations[i].name);
				  }
				}
		    }
	  }
});