<?php

namespace App\Http\Controllers;

use App\Models\Account;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;

class Auth extends Controller
{
    public function __construct() {
      parent::__construct();
    }

    public function register(Request $req) {
        $req->merge(['password' => Hash::make($req->password)]);
        $account = Account::create($req->only('email', 'username', 'nama', 'password'));
        return $this->response->sendToken($account);
    }

    public function login(Request $req) {
        $account = Account::where('username', $req->username)->first();

        if ($account && Hash::check($req->password, $account->password)) {
            return $this->response->sendToken($account);
        }

        return $this->response->forbidden('username/password salah');
    }

}
