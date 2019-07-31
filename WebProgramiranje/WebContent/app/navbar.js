Vue.component("navbar", {
	data: function () {
		    return {
		    	loggedIn: false
		    }
	},
	template: ` 
	<div>
		<div class="navbar" v-if="!loggedIn">
			<ul>
				<li><a href="./">WebTravel</a></li>
				<li style="float:right"><a href="./register">Register</a></li>
				<li style="float:right"><a href="./#/login">Log In</a></li>
			</ul>
		</div>		
		<div class="navbar" v-if="loggedIn">
			<ul>
				<li><a href="./">WebTravel</a></li>
				<li style="float:right"><a href="" v-on:click="logout()">Log Out</a></li>
				<li style="float:right"><a href="contact.asp">Profile</a></li>
			</ul>
		</div>	  
</div>
`
	, 
	mounted() {
        this.loggedIn = localStorage.getItem("loggedUser") !== "";
    },
	methods : {
		logout: function() {
			localStorage.setItem("loggedUser","");
			this.$router.push("/");
		}
	},
});