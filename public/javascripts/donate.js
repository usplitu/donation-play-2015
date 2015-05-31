$('.ui.dropdown')
  .dropdown()
;

//$('.ui.dropdown').dropdown({
// onChange: submit
//});
//$('.dropdown')
//  .dropdown({
//    action: function(text, value) {
//      var t = text;
//      var v = value;
//    }
//  });
//;

$('.ui.form')
.form({
	
   candidateEmail: {
    identifier: 'candidateEmail',
    rules: [{
        type: 'empty',
        prompt: 'Please select a Candidate to whom you wish to make a donation'
      }]
  },

  amountDonated: {
    identifier: 'amountDonated',
    rules: [{
        type: 'empty',
        prompt: 'Please select an amount to donate'
      }]
  },
 
});

//function submitForm()
//{
//	alert("in submit");
//}

//$('.ui.blue.submit.button').on('click', function() {
//	  submitForm();
//	  return false; //doesn't prevent auto refresh...damn
//	});

	function submitForm() {
	  var formData = $('.ui.form.segment input').serialize(); 
	  $.ajax({
	    type: 'POST',
	    url: '/donation/donate',
	    data: formData,
		success: function(response) {            
			//alert("success");
			console.log("make donation page submitForm response: " + response);
		}
	  });
	}
//$.('#donationform')
//{
//	alert("in submit");
//	//$.post("/donation/donate");
//}
//function submit() {
//    new Request.HTML({
//        url: '/echo/html/',
//        data: {
//            html: newOptions,
//            delay: 1
//        },
//        method: 'post',
//        onSuccess: function(response) {            
//            $('.ui.dropdown select').html(newOptions);
//        }
//    }).send();
//}

