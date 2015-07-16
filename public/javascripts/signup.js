$('.ui.dropdown').dropdown();
$('.ui.checkbox').checkbox();

$('.ui.form')
  .form({
  	
    'user.usCitizen': {
      identifier : 'user.usCitizen',
      rules: [
        {
          type   : 'checked',
          prompt : 'You must tick to confirm you are a US citizen'
        }
      ]
    },
  	
    'user.firstName': {
      identifier  : 'user.firstName',
      rules: [
        {
          type   : 'empty',
          prompt : 'Please enter your first name'
        }
      ]
    },
    'user.lastName': {
      identifier  : 'user.lastName',
      rules: [
        {
          type   : 'empty',
          prompt : 'Please select your last name'
        }
      ]
    },
    'user.age': {
      identifier : 'user.age',
      rules: [
        {
          type   : 'empty',
          prompt : 'Please enter your age'
        }
      ]
    },
    'user.state': {
      identifier : 'user.state',
      rules: [
        {
          type   : 'empty',
          prompt : 'Please enter the US state in which you live'
        }
      ]
    },
    
    'user.email': {
      identifier : 'user.email',
      rules: [
        {
          type   : 'empty',
          prompt : 'Please enter your email'
        }
      ]
    },
    
    'user.password': {
      identifier : 'user.password',
      rules: [
        {
          type   : 'empty',
          prompt : 'Please enter a password'
        },
        {
          type   : 'length[6]',
          prompt : 'Your password must be at least 6 characters'
        }]
    }
  },
  
  {
    onSuccess : function() {
	  submitForm();
	  return false; 
    }    
  }
  
  ); 


function submitForm() {
  var formData = $('.ui.form.segment input').serialize(); 
  $.ajax({
    type: 'POST',
    url: '/register',
    data: formData,
	  success: function(response) {            
		  console.log("registration response " + response.registerResponse);
		  if(response.registerResponse === 'nameTaken')
		  {
			  $('#nameTaken').append('<div class="ui pointing left red label">That name is already registered</div>');
	      }
		  else if(response.registerRespone === 'success')
		  {
			  $('#nameTaken').append('<div class="ui pointing left label">Registration succeeded</div>');
	      }
	  }
  });
}
