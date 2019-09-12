Vue.component("reservations", {
	data: function () {
		    return {
		    	reservations: []
		    }
	},
	template: ` 
<div>	
	<div class="centered1">
	<div class="centered2">
	<h3 class="h3">Reservations</h3>	
	<table class="table">
		<tr>
			<th>Flight Number</th>
			<th>Starting Destination</th>
			<th>Arrival Destination</th>
			<th>Flight Date</th>
			<th>Plane Model</th>
			<th>Reservation ID</th>
			<th>User</th>
			<th>Number Of Passengers </th>
			<th>Seat Class</th>
			<th>Reserved</th>
			<th>Options</th>
		</tr>
		<tr v-for="(r,index) in reservations">
			<td>{{r.flightNumber}}</td>
			<td>{{r.startDestination}}</td>
			<td>{{r.arrivalDestination}}</td>
			<td>{{new Date(r.flightDate)}}</td>
			<td>{{r.planeModel}}</td>
			<td>{{r.id}}</td>
			<td>{{r.user}}</td>
			<td>{{r.numberOfPassengers}}</td>
			<td v-if="r.seatClass == 0">First</td>
			<td v-if="r.seatClass == 1">Business</td>
			<td v-if="r.seatClass == 2">Economy</td>
			<td>{{new Date(r.date)}}</td>
			<td><button v-on:click="del(r,index)" class="buttonR">Delete</button></td>
		</tr>
	</table>
	
	</div>
	</div>	
</div>	  
`
	,
	mounted() {
		axios.get("rest/reservation/getReservations")
			.then(response => {
				this.reservations = response.data;
			});
	},
	methods : {
		del: function(r, index) {
			var today = new Date();
			if(r.flightDate - today.getTime() < 0) {
				toast("You can not delete past reservations.");
				return;
			}
			axios.delete("rest/reservation/deleteReservation/" + r.id)
				.then(response => {
					if(response.data == true) {
						toast("Successfully deleted reservation " + r.id);
						this.reservations.splice(index,1);
					} else {
						toast(response.data);
					}
				});
			
		}
	},
});