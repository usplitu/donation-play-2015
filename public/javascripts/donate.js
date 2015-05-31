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
  }
},

{
//	inline : true,
//	on     : 'blur',
	onSuccess : function(){
	    console.log('on success');
	    submitForm();
	    return false;
	} 
});


	function submitForm() {
	  var formData = $('.ui.form.segment input').serialize(); 
	  $.ajax({
	    type: 'POST',
	    url: '/donation/donate',
	    data: formData,
		success: function(response) {            
			console.log("make donation page submitForm response: " + response.progress);
			$('.ui.indicating.progress').progress({
				  percent: response.progress
				});
			$('#progresslabel').text(response.progress + " % of target achieved to date for candidate")
		}
	  });
	}

