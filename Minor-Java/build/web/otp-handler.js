//function OTPHandler(otpSection, timerDisplay) {
//    this.otpSection = otpSection;
//    this.timerDisplay = timerDisplay;
//    this.timerInterval = null;
//    this.otpExpired = false;
//}
//
//// Start the timer for OTP expiration
//OTPHandler.prototype.startTimer = function(duration) {
//    duration = duration || 120;  // Default to 120 seconds if no duration is provided
//    clearInterval(this.timerInterval);
//    this.otpExpired = false;
//    var timer = duration;
//
//    var self = this;  // To preserve 'this' context inside setInterval
//
//    this.timerInterval = setInterval(function() {
//        var minutes = Math.floor(timer / 60);
//        var seconds = timer % 60;
//
//        self.timerDisplay.textContent = 
//            minutes.toString().padStart(2, '0') + ':' + seconds.toString().padStart(2, '0');
//
//        if (--timer < 0) {
//            clearInterval(self.timerInterval);
//            self.timerDisplay.textContent = 'OTP Expired';
//            self.otpExpired = true;
//        }
//    }, 1000);
//};
//
//// Show the OTP section and start the timer
//OTPHandler.prototype.showOTPSection = function() {
//    this.otpSection.classList.remove('hidden');
//    this.startTimer();
//};
//
//// Resend OTP and restart the timer
//OTPHandler.prototype.resendOTP = function() {
//    // Implement OTP resend logic here
//    this.startTimer();
//};
//
//// Verify the OTP entered by the user
//OTPHandler.prototype.verifyOTP = function(otp) {
//    if (this.otpExpired) {
//        return { isValid: false, message: 'OTP has expired. Please request a new one.' };
//    }
//    // Implement OTP verification logic here
//    return { isValid: true };
//};
//
//// Example of instantiating the OTPHandler
//var otpHandler = new OTPHandler(document.getElementById('otpSection'), document.getElementById('timerDisplay'));
