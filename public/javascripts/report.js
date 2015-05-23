$('.ui.dropdown')
  .dropdown()
;

$('.ui.form')
.form({
  candidateEmail: {
    identifier: 'candidateEmail',
    rules: [
      {
        type: 'empty',
        prompt: 'Please select a candidate'
      }
    ]
  }
})
;


 