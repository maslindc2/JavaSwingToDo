$recepient = $args[0]

Write-Output "Cleaning Project"
mvn clean 2>&1 | Out-Null
Write-Output "Cleaning Finished"

Write-Output "Compiling Project"
mvn compiler:compile > build.txt 2>&1 | Out-Null
Write-Output "Finished Compiling"

Write-Output "Running Maven Test"
mvn test > test.txt 2>&1 | Out-Null
Write-Output "Maven Test Finished"

$buildTextPath = "build.txt"
$testTextPath = "test.txt"

$buildResultSubString = "BUILD"
$testResultSubString = "Tests run:"

$buildPassFail = ""
$testPassFail = ""

$buildResults = (Select-String -Pattern $buildResultSubString -Path $buildTextPath -CaseSensitive).Line
$testResults = (Select-String -Pattern $testResultSubString -Path $testTextPath | Select-Object -Last 1).Line

$testInternalSimplified = (Select-String -Pattern "\[ERROR\] ToDoTest\." -Path $testTextPath).Line | ForEach-Object { $_ + "`n" };
$testGUISimplified = (Select-String -Pattern "\[ERROR\] ToDoGUITest\." -Path $testTextPath).Line | ForEach-Object { $_ + "`n" };


# Formatting the output for Build Results
if ($buildResults -clike "*SUCCESS*") {
    $buildResults = $buildResults -replace "\[INFO\]\s*", ""
    $buildPassFail = "True"
} elseif($buildResults -clike "*FAILURE*") {
    $buildResults = $buildResults -replace "\[INFO\]\s*", ""
    $buildPassFail = "False"
}else {
    $buildPassFail = "Status Unknown"
}

# Formatting the output for Test Results
if ($testResults -clike "*ERROR*") {
    $testResults = $testResults -replace "\[ERROR\]\s*", ""
    $testPassFail =  "False"
} elseif ($testResults -clike "*INFO*") {
    $testResults = $testResults -replace "\[INFO\]\s*", ""
    $testPassFail =  "True"
}else {
    $testPassFail = "Failure Occured"
}

$smtpServer = "smtp.office365.com"                                  
$smtpPort = 587                                                     
$smtpUsername = "email address for the account send test results from"
$smtpPassword = "password for the account to send test results from"
$subject = "Team 4 Regression Results"

# If the tests set the body of the email to contain the build results and the test results
# Else the tests have failed, set the body to show the build and test results and state which tests failed
$body = ""
if ($testPassFail -eq "True") {
    $body = "Build Pass: " + $buildPassFail + "`n" + $buildResults + "`n" + "Test Pass: " + $testPassFail + "`n" + $testResults
}elseif($buildPassFail -eq "False"){
    $body = "Build Pass: " + $buildPassFail + "`n" + $buildResults + "`n" + "Test Pass: " + $testPassFail + "`n" + $testResults
}else {
    $body = "Build Pass: " + $buildPassFail + "`n" + $buildResults + "`n" + "Test Pass: " + $testPassFail + "`n" + $testResults + "`n" + "Below are the tests that failed" + "`n" +$testInternalSimplified + $testGUISimplified
}


$smtpClient = New-Object System.Net.Mail.SmtpClient($smtpServer, $smtpPort)
$smtpClient.Credentials = New-Object System.Net.NetworkCredential($smtpUsername, $smtpPassword)  
$smtpClient.EnableSsl = $true  
$mailMessage = New-Object System.Net.Mail.MailMessage($smtpUsername, $recepient, $subject, $body)  
$smtpClient.Send($mailMessage)  

$mailMessage.Dispose()
$smtpClient.Dispose()

Remove-Item -Path "test.txt"
Remove-Item -Path "build.txt"