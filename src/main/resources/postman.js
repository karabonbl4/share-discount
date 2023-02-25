const jwtRequest = {
    url: 'http://localhost:8080/auth',
    method: 'POST',
    header: 'Content-Type:application/json',
    body: {
    mode: 'row',
    raw: JSON.stringify({"username": "user", "password": "password"})
  }
};
pm.sendRequest(jwtRequest, (err, res) => {
    const access_token  = res.json().jwtToken;
    pm.globals.set('ACCESS_TOKEN', access_token);
    console.log('ACCESS_TOKEN: ${access_token}');
});