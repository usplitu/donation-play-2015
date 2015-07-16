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
          prompt : 'Please enter your last name'
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
        },
        {
        	type : 'email',
        	prompt : 'Please enter a valid email address'
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
  },
  // http://semantic-ui.com/behaviors/form.html#/examples
  'geolocation.latitude': {
    identifier : 'geolocation.latitude',
    rules: [
      {
      	type: 'empty',
      	prompt: 'Please enter your latitude'
      },
      {
        type   : 'isValidLatitude',
        prompt : 'Please enter latitude: valid range -90.0 to +90.0'
      }]
  },
  
  'geolocation.longitude': {
    identifier : 'geolocation.longitude',
    rules: [
      {
         type: 'empty',
         prompt: 'Please enter your longitude'
      },            
      {
        type   : 'isValidLongitude',
        prompt : 'Please enter longitude: valid range -180 to +180'
      }]
    }
  }, 
  
  {
    onSuccess : function() {
    submitForm();
    return false; 
    }    
  }); 

/**
 * store form data in variable named formData
 * post form data
 * on return from ajax call:
 *    check if label div present
 *        if label div present detach it
 *    if response confirms registration failed because name taken:
 *        recreate div with red label indicating registration failed and why
 *    if response confirms registration succeeded then:
 *        recreate div with teal label indication successful registration
 *    
 * dynamically create new label div 
 */
function submitForm() {
  var formData = $('.ui.form.segment input').serialize(); 
  $.ajax({
    type: 'POST',
    url: '/register',
    data: formData,
    success: function(response) {            
      console.log("registration response " + response.registerResponse);
      if ($('#registerLabel').length) {
        $('#registerLabel').detach();
      }
      if(response.registerResponse === 'nameTaken') {
        $('#nameTaken').append('<div id="registerLabel" class="ui pointing left red label">That name is already registered</div>');
      }
      else if(response.registerResponse === 'registered') {
        $('#nameTaken').append('<div id="registerLabel" class="ui pointing left teal label">Registration succeeded</div>');
      }
    }
  });
}
