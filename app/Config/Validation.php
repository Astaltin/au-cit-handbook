<?php

namespace Config;

use CodeIgniter\Config\BaseConfig;
use CodeIgniter\Validation\StrictRules\CreditCardRules;
use CodeIgniter\Validation\StrictRules\FileRules;
use CodeIgniter\Validation\StrictRules\FormatRules;
use CodeIgniter\Validation\StrictRules\Rules;

class Validation extends BaseConfig
{
    // --------------------------------------------------------------------
    // Setup
    // --------------------------------------------------------------------
    /**
     * Stores the classes that contain the
     * rules that are available.
     *
     * @var string[]
     */
    public array $ruleSets = [
        Rules::class,
        FormatRules::class,
        FileRules::class,
        CreditCardRules::class,
    ];

    /**
     * Specifies the views that are used to display the
     * errors.
     *
     * @var array<string, string>
     */
    public array $templates = [
        'list'   => 'CodeIgniter\Validation\Views\list',
        'single' => 'CodeIgniter\Validation\Views\single',
    ];

    // --------------------------------------------------------------------
    // Rules
    // --------------------------------------------------------------------
    public array $register = [
        'familyName' => 'required|min_length[2]|max_length[255]|alpha',
        'givenName' => 'required|min_length[2]|max_length[255]|alpha',
        'email' => 'required|min_length[3]|max_length[255]|is_unique[accounts.email]|valid_email',
        'password' => 'required|min_length[8]|max_length[255]',
        'passwordConfirm' => 'required|max_length[255]|matches[password]',
    ];

    public array $login = [
        'email' => 'required|max_length[255]|valid_email',
        'password' => 'required|max_length[255]'
    ];
}
