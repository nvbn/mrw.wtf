from fabric.api import cd, lcd, local, put, run


def deploy():
    local('rm -f mrw-nlp/mrw_nlp/classifier.pickle')
    put('mrw-nlp', '/opt/')
    run('pip3 install -r /opt/mrw-nlp/requirements.txt')
    run('systemctl restart mrw-nlp.service')

    with lcd('mrw-web'):
        local('rm -rf resources/public/production/')
        local('lein cljsbuild once production')
        put('resources/public/index.html', '/var/www/html/')
        put('resources/public/main.css', '/var/www/html/')
        run('rm -rf /var/www/html/compiled')
        run('mkdir -p /var/www/html/compiled')
        put('resources/public/production/main.js', '/var/www/html/compiled/main.js')

    with lcd('mrw-server'):
        local('lein ring uberjar')
        put('target/mrw-server-0.1.0-SNAPSHOT-standalone.jar', '/opt/mrw-server.jar')

    with lcd('mrw-parser'):
        local('lein uberjar')
        put('target/mrw-parser-0.1.0-SNAPSHOT-standalone.jar', '/opt/mrw-parser.jar')

    run('systemctl restart mrw-server.service')


def update_docker_images():
    for project in ['mrw-nlp', 'mrw-parser', 'mrw-server', 'mrw-web']:
        with lcd(project):
            local('docker build . -t nvbn/{}:latest'.format(project))
            local('docker push nvbn/{}:latest'.format(project))
