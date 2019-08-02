Vue.component("destinations", {
	data: function () {
		    return {
		    	destinations: []
		    }
	},
	template: ` 
<div>
	<h3>All Destinations</h3>

	<table border="1">
		<tr>
			<th>Name</th>
			<th>Country</th>
			<th>Airport</th>
			<th>Code</th>
			<th>Coordinates</th>
			<th>Active</th>
		</tr>
		<tr v-for="d in destinations">
			<td>{{d.name}}</td>
			<td>{{d.country}}</td>
			<td>{{d.airport}}</td>
			<td>{{d.airportCode}}</td>
			<td>{{d.coordinates}}</td>
			<td>{{d.active}}</td>
		</tr>
	</table>
	
</div>		  
`
	, 
	mounted() {
		if(localStorage.destinations === "") {
			axios.get("rest/admin/getAllDestinations")
				.then(response => {
					localStorage.destinations = JSON.stringify(response.data);
					this.destinations = response.data;
				});
		} else {
			this.destinations = JSON.parse(localStorage.destinations);
		}
		
	},
	methods : {

	},
});