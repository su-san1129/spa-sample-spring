var app = new vue({
	el: '#app',
	data: {
		return {
			info: null
		}
	},
	mounted(){
		axios.get('http://localhost:8080/').then(response => (this.info = response))
	}
})