<?php

namespace App\Http\Controllers;

use App\Models\Account;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;

class Auth extends Controller
{

    public function register(Request $req) {
        $req->merge(['password' => Hash::make($req->password)]);
        $account = Account::create($req->only('email', 'username', 'name', 'password'));
        return response()->json($account);
    }

    public function login(Request $req) {
        $account = Account::where('username', $req->username)->first();

        if ($account && Hash::check($req->password, $account->password)) {
            return response()->json($account);
        }

        return response()->json(['message' => 'username/password salah']);
    }

}
