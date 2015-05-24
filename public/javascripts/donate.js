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
        prompt: 'Please select a Candidate to whom you wish to make a donation'
      }
    ]
  }
})
;

$('.ui.form')
.form({
  amountDonated: {
    identifier: 'amountDonated',
    rules: [
      {
        type: 'empty',
        prompt: 'Please select an amount to donate'
      }
    ]
  }
})
;