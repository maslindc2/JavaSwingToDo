$recepient = $args[0]

$gitHublocation = "C:\Users\Maslin\Documents\GitHub\Team4-JavaSwingToDo"
Set-Location -Path $gitHublocation

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
$smtpUsername = "email account you are using to send results from "                         
$smtpPassword = "password for the account"                    
$emailSender = "email address you are sending from same as the username"                               
$recipient = $args[0]                                  
$subject = "Team 4 Regression Results" 
$body = "Build Pass: " + $buildPassFail + "`n" + $buildResults + "`n" + "Test Pass: " + $testPassFail + "`n" + $testResults
$smtpClient = New-Object System.Net.Mail.SmtpClient($smtpServer, $smtpPort)  
$smtpClient.Credentials = New-Object System.Net.NetworkCredential($smtpUsername, $smtpPassword)  
$smtpClient.EnableSsl = $true  
$mailMessage = New-Object System.Net.Mail.MailMessage($emailSender, $recipient, $subject, $body)  
$smtpClient.Send($mailMessage)  

Remove-Item .\test.txt
Remove-Item .\build.txt
Set-Location -Path "C:\Users\Maslin\Documents\Documents"