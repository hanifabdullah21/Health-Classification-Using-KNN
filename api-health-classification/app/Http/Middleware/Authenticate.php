<?php

namespace App\Http\Middleware;

use App\Handlers\Response;
use Closure;
use Illuminate\Contracts\Auth\Factory as Auth;
use JWT;

class Authenticate
{
    /**
     * The authentication guard factory instance.
     *
     * @var \Illuminate\Contracts\Auth\Factory
     */
    protected $auth;

    /**
     * Create a new middleware instance.
     *
     * @param  \Illuminate\Contracts\Auth\Factory  $auth
     * @return void
     */
    public function __construct(Auth $auth)
    {
        $this->auth = $auth;
    }

    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @param  string|null  $guard
     * @return mixed
     */
    public function handle($request, Closure $next, $guard = null)
    {
        if ($token = $request->bearerToken()) {
          try {
              $validate = JWT::decode($token, env('JWT_SECRET'), ['HS256']);
              $request->merge(['account' => $validate->sub] );
              return $next($request);
          } catch(\Exception $e) {
              return (new Response)->forbidden('Token is not valid');
          }
        }

        (new Response)->forbidden('Invalid Type Token');
    }
}
