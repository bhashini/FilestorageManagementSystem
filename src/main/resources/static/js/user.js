$(document).ready(function() {

	$("#insert").click(function(event) {


		/*
		* Check if all the text fields contain information before saving it.
		*/
		/* var empty = $(this).parent().find("input").filter(function() {
			 return this.value === "";
		 });
 
		 if (empty.length) {
			 alert("Please, fill out all the fields!");
			 return;
		 }*/

		var email = $("#email").val();
		var name = $("#name").val();
		var password = $("#password").val();
		var bname = $("#bname").val();


		$.ajax({
			url: '/user/',
			data: JSON.stringify({
				'email': email,
				'name': name,
				'bname': bname,
				'password': password

			}),

			type: 'POST', /* Use the HTTP verb PUT to update information in the server. */
			contentType: 'application/json', /* Tell Spring the information comes in JSON format. */
			success: function(data) {
				alert("The information was updated successfully!");
				$("input[type='text']").val(''); /* Clean up the text-box fields of the form. */
				$("input[type='password']").val('');
			},
			error: function() {
				alert("Error while Creating new User!!!");
			}
		});
	});

	$("#query").click(function(event) {

		var email = $("#email").val();

		/*
		 * Check if the Id field is not empty.
		 */
		if (email.trim() === "") {

			alert("Please, type an Email!");

			$("#email").focus();
			return;
		}

		$.ajax({
			url: '/user/' + email,
			data: {
				/*id: id*/
			},
			type: 'GET',
			success: function(data) {

				$('#name').val(data.name);
				$('#password').val(data.password);
				$('#bname').val(data.bname);

			},
			error: function() {
				alert("Error while retrieving Data!!!");
			}
		});


	});


	$("#save").click(function(event) {

        /*
         * Check if all the text fields contain information before saving it.
         */
		var empty = $(this).parent().find("input").filter(function() {
			return this.value === "";
		});

		if (empty.length) {
			alert("Please, fill out all the fields!");
			return;
		}

        /*
         * Proceed if all the fields are complete.
         */

		var email = $("#email").val();
		var name = $("#name").val();
		var password = $("#password").val();
		var bname = $("#bname").val();



		$.ajax({
			url: '/user/' + email,
            /*
             * Create a JSON Object with the information of the Form.
             */
			data: JSON.stringify({
				'email': email,
				'name': name,
				'password': password,
				'bname': bname,

			}),

			type: 'PUT', /* Use the HTTP verb PUT to update information in the server. */
			contentType: 'application/json', /* Tell Spring the information comes in JSON format. */
			success: function(data) {
				alert("The information was updated successfully!");
				$("input[type='text']").val(''); /* Clean up the text-box fields of the form. */
				$("input[type='password']").val('');
			},
			error: function() {
				alert("Error while updating data!!!");
			}
		});
	});


	$("#delete").click(function(event) {

		var email = $("#email").val();

		/*
		 * Check if the Id field is not empty.
		 */
		if (email.trim() === "") {

			alert("Please, type an Email!");

			$("#email").focus();
			return;
		}

		$.ajax({
			url: '/user/' + email,
			data: {
				/*id: id*/
			},
			type: 'DELETE',
			success: function(data) {
				alert("The information was deleted successfully!");
				$("input[type='text']").val(''); 
				
//				$('#name').val(data.name);
//				$('#password').val(data.password);
//				$('#bname').val(data.bname);



			},
			error: function() {
				alert("Error while deleting Data!!!");
			}
		});


	});


	$("#clear").click(function(event) {
		$("input[type='text']").val('');
		$("input[type='password']").val('');

	});

});