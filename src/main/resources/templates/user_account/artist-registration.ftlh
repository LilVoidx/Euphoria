<!DOCTYPE html>
<html lang="en">
<head>
    <title>Artist Registration</title>
</head>
<body>
<h2>Artist Registration</h2>
<form action="/artistregistration" method="post">
    <div>
        <label for="stageName">Stage Name:</label>
        <input type="text" id="stageName" name="stageName" required>
    </div>
    <div>
        <label for="bio">Bio:</label>
        <textarea id="bio" name="bio" rows="4" cols="50" required></textarea>
    </div>
    <div>
        <label for="genre">Genre:</label>
        <label>
            <select name="genre">
                <#list genres as genre>
                    <option value="${genre}">${genre}</option>
                </#list>
            </select>
        </label>

    </div>
    <button type="submit">Submit</button>
</form>
</body>
</html>
