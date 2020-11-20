import flask
from flask import request, jsonify, abort

app = flask.Flask(__name__)

@app.route("/",methods = ["GET"])
def home():
	return "<h1>Manish Patole</h1>"

@app.route("/login", methods = ["POST"])
def login():

	if not request.json:
		return jsonify({"error":"bad_request","description":"Network communication error"}), 400

	name = request.json["name"]
	password = request.json["password"]

	if(name == "test@worldofplay.in" and password == "Worldofplay@2020"):
		return jsonify({"token":"XYZ"}), 200
	else:
		return jsonify({"error":"invalid_credentials","description":"Email address and password is not a valid combination"}), 401


if __name__ == "__main__":
	app.run()
