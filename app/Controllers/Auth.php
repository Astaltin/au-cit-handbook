<?php

namespace App\Controllers;

use App\Controllers\BaseController;
use CodeIgniter\API\ResponseTrait;
use CodeIgniter\Database\Exceptions\DatabaseException;
use ReflectionException;

class Auth extends BaseController
{
    use ResponseTrait;

    public function index()
    {
        return $this->respondNoContent();
    }

    public function login()
    {
        if (!$this->validateData($this->request->getJSON(true), 'login')) {
            return $this->failValidationErrors(null)
                ->setJSON([
                    'loginValidationError' => $this->validator->getErrors()
                ]);
        }
        $data = $this->validator->getValidated();

        try {
            if (!$this->auth->authenticate($data)) {
                return $this->failUnauthorized()
                    ->setJSON(['body' => lang('Auth.loginFailed')]);
            }
        } catch (ReflectionException $e) {

            return $this->failServerError()
                ->setJSON(['body' => lang('Auth.serverError')]);
        }

        return $this->respond()
            ->setJSON(['body' => lang('Auth.loginSuccess')])
            ->setStatusCode(200);
    }

    public function logout()
    {
        try {
            $this->auth->logout();
        } catch (ReflectionException $e) {

            return $this->failServerError()
                ->setJSON(['body' => lang('Auth.serverError')]);
        }

        return $this->respond()
            ->setJSON(['body' => lang('Auth.logoutSuccess')])
            ->setStatusCode(200);
    }

    public function register()
    {
        if (!$this->validateData($this->request->getJSON(true), 'register')) {
            return $this->failValidationErrors(null)
                ->setJSON([
                    'registerValidationError' => $this->validator->getErrors()
                ]);
        }
        $data = $this->validator->getValidated();

        try {
            if (!$this->auth->createUser($data)) {
                return $this->failServerError()
                    ->setJSON(['body' => lang('Auth.registrationFailed')]);
            }
        } catch (DatabaseException $e) {

            return $this->failServerError()
                ->setJSON(['body' => lang('Auth.serverError')]);
        }

        return $this->respondCreated()
            ->setJSON(['body' => lang('Auth.registrationSuccess')]);
    }

    public function forbidden()
    {
        return $this->failForbidden()->setBody(null);
    }
}
